package com.xiaqi.filetransfer.core.client;

import com.xiaqi.filetransfer.Publisher;
import com.xiaqi.filetransfer.core.ConvertUnitUtil;
import com.xiaqi.filetransfer.core.FileHashUtil;
import com.xiaqi.filetransfer.core.TaskOuterClass.Task;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
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

public class NettyClient extends Publisher {
    private Channel heartBeatChannel;

    private EventLoopGroup group;

    private String folderPath;

    private CompletableFuture<String> future = new CompletableFuture();

    private String ip;

    private ConcurrentHashMap<String, CompletableFuture<BiConsumer<Double, String>>> uploadFutures = new ConcurrentHashMap();

    private ConcurrentHashMap<String, CompletableFuture<BiConsumer<Double, String>>> downloadFutures = new ConcurrentHashMap();

    private Map<String, Task> downloadTasks = new ConcurrentHashMap<>();

    private Map<String, Task> uploadTasks = new ConcurrentHashMap<>();

    private CopyOnWriteArrayList<Thread> threads = new CopyOnWriteArrayList();

    private Map<String, SocketChannel> channels = new ConcurrentHashMap<>();

    public void start(String ip) {
        this.ip = ip;
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new ProtobufVarint32FrameDecoder());
                            pipeline.addLast(new ProtobufDecoder(Task.getDefaultInstance()));
                            pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
                            pipeline.addLast(new ProtobufEncoder());
                            pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new IdleStateHandler(0, 3, 0, TimeUnit.SECONDS));
                            pipeline.addLast(new ClientHeartbeatHandler());
                            update("action4");
                        }
                    });

            ChannelFuture f = b.connect(ip, 8080).sync();
            heartBeatChannel = f.channel();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public ChannelFuture createFileChannel(String fileName) {
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new MonitorOutboundHandler());
                            pipeline.addLast(new ChunkedWriteHandler());
                            pipeline.addLast(new ClientFileOutboundHandler());
                            pipeline.addLast(new MonitorInboundHandler());
                            pipeline.addLast(new ClientFileHandler(fileName));
                        }
                    });

            ChannelFuture f = b.connect(this.ip, 8080).sync();
            return f;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void sendFile(File file) {
        String fileNmae = file.getName();
        System.out.println(file.getName());
        CompletableFuture<BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>>> setHashFuture = new CompletableFuture();
        update("action8", fileNmae, setHashFuture);
        group.submit(() -> {
            try {
                ChannelFuture channelFuture = createFileChannel(null);
                Channel channel = channelFuture.channel();

                String hash = FileHashUtil.getHash(file);
                Task task = Task.newBuilder()
                        .setHash(hash)
                        .setFileName(fileNmae)
                        .setFilePath(file.getAbsolutePath())
                        .setSize(file.length())
                        .setAddress(channel.localAddress().toString())
                        .setDirection("client2server")
                        .build();
                uploadTasks.put(hash, task);
                BiConsumer setHash = setHashFuture.get();
                CompletableFuture<BiConsumer<Double, String>> setProgressFuture = new CompletableFuture<>();
                setHash.accept(hash, setProgressFuture);
                uploadFutures.put(hash, setProgressFuture);

                heartBeatChannel.writeAndFlush(task);

                channel.writeAndFlush(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void shutdown() {
        group.shutdownGracefully();
    }

    class ClientFileOutboundHandler extends ChannelOutboundHandlerAdapter {
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
                    if (ctx.channel().localAddress().toString().equals(t.getAddress())) {
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
                    if (ctx.channel().localAddress().toString().equals(t.getAddress())) {
                        task = t;
                    }
                }
            }
            if (setProgress == null) {
                setProgress = downloadFutures.get(task.getHash()).get();
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

    class ClientFileHandler extends ChannelInboundHandlerAdapter {
        private OutputStream outputStream;
        private String fileName;

        public ClientFileHandler(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (folderPath == null) {
                update("action6", future);
                folderPath = future.get();
            }
            if (outputStream == null) {
                Path path = Paths.get(folderPath, fileName);
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
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            ctx.flush();
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
        }
    }

    class ClientHeartbeatHandler extends SimpleChannelInboundHandler<Task> {
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent) {
                IdleStateEvent event = (IdleStateEvent) evt;
                if (event.state() == IdleState.READER_IDLE) {
                    System.out.println("服务端长时间未发送消息，关闭连接");
                    ctx.close();
                    update("action5");
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
            String fileName  = task.getFileName();
            if (task != null && !"".equals(fileName)) {
                ChannelFuture channelFuture = createFileChannel(fileName);
                Channel channel = channelFuture.channel();

                CompletableFuture<BiConsumer<Double, String>> setProgressFuture = new CompletableFuture<>();
                update("action7", task.getHash(), task.getFileName(), setProgressFuture);
                downloadFutures.put(task.getHash(), setProgressFuture);

                Task newTask = Task.newBuilder()
                        .setHash(task.getHash())
                        .setFileName(fileName)
                        .setFilePath(task.getFilePath())
                        .setSize(task.getSize())
                        .setAddress(channel.localAddress().toString())
                        .setDirection(task.getDirection())
                        .build();
                downloadTasks.put(task.getHash(), newTask);
                heartBeatChannel.writeAndFlush(newTask);
            } else {
                System.out.println("connected");
            }
        }

        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            cause.printStackTrace();
            ctx.close();
            update("action5");
        }
    }
}
