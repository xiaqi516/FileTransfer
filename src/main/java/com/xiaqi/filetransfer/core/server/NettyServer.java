package com.xiaqi.filetransfer.core.server;

import com.xiaqi.filetransfer.Publisher;
import com.xiaqi.filetransfer.core.ConvertUnitUtil;
import com.xiaqi.filetransfer.core.FileHashUtil;
import com.xiaqi.filetransfer.core.TaskOuterClass.Task;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.stream.ChunkedFile;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.function.BiConsumer;

public class NettyServer extends Publisher {
    private Boolean isHeartBeat = true;

    private SocketChannel heartBeatChannel;

    private CompletableFuture<String> future = new CompletableFuture();

    private ConcurrentHashMap<String, CompletableFuture<BiConsumer<Double, String>>> uploadFutures = new ConcurrentHashMap();

    private ConcurrentHashMap<String, CompletableFuture<BiConsumer<Double, String>>> downloadFutures = new ConcurrentHashMap();

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private Map<String, SocketChannel> channels = new ConcurrentHashMap<>();

    private Map<String, Task> uploadTasks = new ConcurrentHashMap<>();

    private Map<String, Task> downloadTasks = new ConcurrentHashMap<>();

    private CopyOnWriteArrayList<Thread> threads = new CopyOnWriteArrayList();

    private String folderPath;

    public void start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if (isHeartBeat) {
                                pipeline.addLast(new ProtobufVarint32FrameDecoder());
                                pipeline.addLast(new ProtobufDecoder(Task.getDefaultInstance()));
                                pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                                pipeline.addLast(new ProtobufEncoder());
                                pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                                pipeline.addLast(new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS));
                                pipeline.addLast(new ServerHeartbeatHandler());
                                update("action1", ch.remoteAddress().getHostName());
                                heartBeatChannel = ch;
                                isHeartBeat = false;
                            } else {
                                pipeline.addLast(new MonitorOutboundHandler());
                                pipeline.addLast(new ChunkedWriteHandler());
                                pipeline.addLast(new ServerFileOutboundHandler());
                                pipeline.addLast(new MonitorInboundHandler());
                                pipeline.addLast(new ServerFileHandler());
                                channels.put(ch.remoteAddress().toString(), ch);
                                for (Thread thread : threads) {
                                    LockSupport.unpark(thread);
                                }
                            }
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .bind(8080);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public void sendFile(File file) {
        String fileNmae = file.getName();
        System.out.println(file.getName());
        CompletableFuture<BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>>> setHashFuture = new CompletableFuture();
        update("action3", fileNmae, setHashFuture);
        workerGroup.submit(() -> {
            try {
                String hash = FileHashUtil.getHash(file);
                Task task = Task.newBuilder()
                        .setHash(hash)
                        .setFileName(fileNmae)
                        .setFilePath(file.getAbsolutePath())
                        .setSize(file.length())
                        .setDirection("server2client")
                        .build();
                uploadTasks.put(hash, task);
                threads.add(Thread.currentThread());
                BiConsumer setHash = setHashFuture.get();
                CompletableFuture<BiConsumer<Double, String>> setProgressFuture = new CompletableFuture<>();
                setHash.accept(hash, setProgressFuture);
                uploadFutures.put(hash, setProgressFuture);
                heartBeatChannel.writeAndFlush(task);

                Task task1 = uploadTasks.get(hash);
                while (task1.getAddress() == null || !channels.containsKey(task1.getAddress())) {
                    LockSupport.park();
                    task1 = uploadTasks.get(hash);
                }

                Channel channel = channels.get(task1.getAddress());
                channel.writeAndFlush(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    class MonitorInboundHandler extends ChannelInboundHandlerAdapter {
        private long transferred;
        private Task task;
        private BiConsumer<Double, String> setProgress;
        private long lastTime;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            long duration = 0;
            long currentTime = System.currentTimeMillis();
            if (lastTime == 0) {
                lastTime = System.currentTimeMillis();
            } else {
                duration = currentTime - lastTime;
            }

            if (task == null) {
                for (Task t : downloadTasks.values()) {
                    if (ctx.channel().remoteAddress().toString().equals(t.getAddress())) {
                        task = t;
                    }
                }
            }
            if (setProgress == null) {
                threads.add(Thread.currentThread());
                for (;;) {
                    setProgress = downloadFutures.get(task.getHash()).get();
                    if (setProgress == null) {
                        LockSupport.park();
                    } else {
                        break;
                    }
                }
            }
            transferred += ((ByteBuf) msg).readableBytes();
            if (duration > 50 || transferred == task.getSize()) {
                double progress = (double) transferred / (double) task.getSize();
                String transferredStr = ConvertUnitUtil.convertUnitStr(transferred) + "/" + ConvertUnitUtil.convertUnitStr(task.getSize());
                setProgress.accept(progress, transferredStr);
                lastTime = currentTime;
            }
            ctx.fireChannelRead(msg);
        }
    }

    class MonitorOutboundHandler extends ChannelOutboundHandlerAdapter {
        private long transferred;
        private Task task;
        private BiConsumer<Double, String> setProgress;
        private long lastTime;

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            long duration = 0;
            long currentTime = System.currentTimeMillis();
            if (lastTime == 0) {
                lastTime = System.currentTimeMillis();
            } else {
                duration = currentTime - lastTime;
            }

            if (task == null) {
                for (Task t : uploadTasks.values()) {
                    if (ctx.channel().remoteAddress().toString().equals(t.getAddress())) {
                        task = t;
                    }
                }
            }
            if (setProgress == null) {
                setProgress = uploadFutures.get(task.getHash()).get();
            }
            transferred += ((ByteBuf) msg).readableBytes();
            if (duration > 50 || transferred == task.getSize()) {
                double progress = (double) transferred / (double) task.getSize();
                String transferredStr = ConvertUnitUtil.convertUnitStr(transferred) + "/" + ConvertUnitUtil.convertUnitStr(task.getSize());
                setProgress.accept(progress, transferredStr);
                lastTime = currentTime;
            }
            super.write(ctx, msg, promise);
        }
    }

//    class ServerFileInboundHandler extends ChannelInboundHandlerAdapter {
//        @Override
//        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//            System.out.println(ctx.channel().remoteAddress());
//            ByteBuf buf = (ByteBuf) msg;
//            int status = buf.readInt();
//            if (status == 2) {
//                System.out.println("开始发送文件");
//                RandomAccessFile raf = new RandomAccessFile(file, "r");
//                System.out.println(file.getAbsolutePath());
//                ctx.writeAndFlush(new ChunkedFile(raf));
//            }
//        }
//
//        @Override
//        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//            cause.printStackTrace();
//            ctx.close();
//        }
//    }

    class ServerFileHandler extends ChannelInboundHandlerAdapter {
        private OutputStream outputStream;

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (folderPath == null) {
                update("action6", future);
                folderPath = future.get();
            }

            if (outputStream == null) {
                threads.add(Thread.currentThread());
                Task task = null;
                for (;;) {
                    for (Task t : downloadTasks.values()) {
                        if (ctx.channel().remoteAddress().toString().equals(t.getAddress())) {
                            task = t;
                            break;
                        }
                    }
                    if (task == null) {
                        LockSupport.park();
                    } else {
                        break;
                    }
                }

                Path path = Paths.get(folderPath, task.getFileName());
                outputStream = new FileOutputStream(path.toString());
            }

            ByteBuf buf = (ByteBuf) msg;
            try {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                // 将接收到的数据写入本地文件
                outputStream.write(bytes);
            } finally {
                // 释放 ByteBuf
                buf.release();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    class ServerFileOutboundHandler extends ChannelOutboundHandlerAdapter {
        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            File file = (File) msg;
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            System.out.println(file.getAbsolutePath());
            ctx.writeAndFlush(new ChunkedFile(raf));
            super.write(ctx, msg, promise);
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    class ServerHeartbeatHandler extends SimpleChannelInboundHandler<Task> {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.READER_IDLE) {
                    System.out.println("客户端长时间未发送消息，关闭连接");
                    ctx.close();
                    update("action2");
                } else if (event.state() == IdleState.WRITER_IDLE) {
                    Task task = Task.newBuilder().build();
                    ctx.writeAndFlush(task);
                }
            } else {
                super.userEventTriggered(ctx, evt);
            }
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, Task task) throws Exception {
            if ("".equals(task.getFileName())) {
                System.out.println("connected");
            } else {
                if ("server2client".equals(task.getDirection())) {
                    uploadTasks.put(task.getHash(), task);
                } else {
                    downloadTasks.put(task.getHash(), task);

                    CompletableFuture<BiConsumer<Double, String>> setProgressFuture = new CompletableFuture<>();
                    update("action7", task.getHash(), task.getFileName(), setProgressFuture);
                    downloadFutures.put(task.getHash(), setProgressFuture);
                }
                for (Thread thread : threads) {
                    LockSupport.unpark(thread);
                }
                System.out.println("unpark");
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
            update("action2");
        }
    }
}
