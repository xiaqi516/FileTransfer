package com.xiaqi.filetransfer.gui.main;

import com.xiaqi.filetransfer.Listener;
import com.xiaqi.filetransfer.core.client.NettyClient;
import com.xiaqi.filetransfer.core.server.NettyServer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class MainController extends Listener {
    private String type;

    private NettyServer server;

    private NettyClient client;

    @FXML
    private TextField ipText;

    @FXML
    private Button connectButton;

    @FXML
    private Label statusLabel;

    @FXML
    private Button fileButton;

    @FXML
    private VBox uploadTaskVBox;

    @FXML
    private VBox downloadTaskVBox;

    @Override
    public void update(String code, Object... args) {
        if ("action1".equals(code)) {
            String ip = (String) args[0];
            serverConnectedAction(ip);
        } else if ("action2".equals(code)) {
            serverDisconnectedAction();
        } else if ("action3".equals(code)) {
            String hash = (String) args[0];
            CompletableFuture future = (CompletableFuture) args[1];
            addServerTaskBarTaskAction(hash, future);
        } else if ("action4".equals(code)) {
            clientConnectedAction();
        } else if ("action5".equals(code)) {
            clientDisconnectedAction();
        } else if ("action6".equals(code)) {
            CompletableFuture future = (CompletableFuture) args[0];
            getFolderPathAction(future);
        } else if ("action7".equals(code)) {
            String hash = (String) args[0];
            String fileName = (String) args[1];
            CompletableFuture future = (CompletableFuture) args[2];
            addClientTaskBarTaskAction(hash, fileName, future);
        } else if ("action8".equals(code)) {
            String hash = (String) args[0];
            CompletableFuture future = (CompletableFuture) args[1];
            addClientUploadTaskBar(hash, future);
        }
    }

    public void initialize() {
        Platform.runLater(() -> {
            MainStage mainStage = (MainStage) ipText.getScene().getWindow();
            type = mainStage.getType();
            System.out.println(type);
            if ("server".equals(type)) {
                ipText.setManaged(false);
                ipText.setVisible(false);
                connectButton.setManaged(false);
                connectButton.setVisible(false);

                server = new NettyServer();
                server.subscribe(this);
                server.start();

                mainStage.setOnCloseRequest(event -> {
                    server.shutdown();
                });
            }
        });
    }

    private void serverConnectedAction(String ip) {
        Platform.runLater(() -> {
            ipText.setManaged(true);
            ipText.setVisible(true);
            ipText.setDisable(true);
            ipText.setText(ip);
            statusLabel.setText("已连接");
            fileButton.setDisable(false);
        });
    }

    private void serverDisconnectedAction() {
        Platform.runLater(() -> {
            ipText.setManaged(false);
            ipText.setVisible(false);
            statusLabel.setText("未连接");
            fileButton.setDisable(true);
        });
    }

    private void addServerTaskBarTaskAction(String hash, CompletableFuture<BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>>> future) {
        Platform.runLater(() -> {
            this.addServerUploadTaskBar(hash, future);
        });
    }

    private void addClientTaskBarTaskAction(String hash, String fileName, CompletableFuture<BiConsumer<Double, String>> future) {
        Platform.runLater(() -> {
            this.addClientDownloadTaskBar(hash, fileName, future);
        });
    }

    @FXML
    private void connect() {
        MainStage mainStage = (MainStage) ipText.getScene().getWindow();
        type = mainStage.getType();
        ipText.setDisable(true);
        connectButton.setDisable(true);
        statusLabel.setText("连接中");
        String ip = ipText.getText();
        if (!"".equals(ip)) {
            client = new NettyClient();
            client.subscribe(this);
            client.start(ip);
            mainStage.setOnCloseRequest(event -> {
                client.shutdown();
            });
        }
    }

    private void clientConnectedAction() {
        Platform.runLater(() -> {
            ipText.setDisable(true);
            statusLabel.setText("已连接");
            fileButton.setDisable(false);
        });
    }

    private void clientDisconnectedAction() {
        Platform.runLater(() -> {
            ipText.setDisable(false);
            statusLabel.setText("未连接");
            connectButton.setDisable(false);
            fileButton.setDisable(true);
        });
    }

    private void getFolderPathAction(CompletableFuture<String> future) {
        Platform.runLater(() -> {
            future.complete(getFolderPath());
        });
    }

    private void addClientDownloadTaskBar(String hash, String fileName, CompletableFuture<BiConsumer<Double, String>> future) {
        final VBox vBox = new VBox();
        vBox.setId(hash);
        vBox.setStyle("-fx-border-color: #8ac6ef;-fx-border-width: 2px;-fx-border-radius: 3px;");
        // 文件名
        Label fileNameLabel = new Label(fileName);
        vBox.getChildren().add(fileNameLabel);
        // Hash值
//        final Label hashLabel = new Label();
//        hashLabel.setText("");
//        vBox.getChildren().add(hashLabel);
        // 进度文字
        final Label progresslabel = new Label();
        progresslabel.setText("");
        vBox.getChildren().add(progresslabel);
        // 进度条
        final ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(Double.MAX_VALUE);
        progressBar.setProgress(-1);
        vBox.getChildren().add(progressBar);
        downloadTaskVBox.getChildren().add(vBox);

        BiConsumer<Double, String> setProgress = (progress, transferred) -> {
            Platform.runLater(() -> {
                progressBar.setProgress(progress);
                progresslabel.setText(transferred);
            });
        };
        future.complete(setProgress);
    }

    private void addClientUploadTaskBar(String fileName, CompletableFuture<BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>>> future) {
        final VBox vBox = new VBox();
        vBox.setStyle("-fx-border-color: #8ac6ef;-fx-border-width: 2px;-fx-border-radius: 3px;");
        // 文件名
        Label fileNameLabel = new Label(fileName);
        vBox.getChildren().add(fileNameLabel);
        // Hash值
//        final Label hashLabel = new Label();
//        hashLabel.setText("");
//        vBox.getChildren().add(hashLabel);
        // 进度文字
        final Label progresslabel = new Label();
        progresslabel.setText("");
        vBox.getChildren().add(progresslabel);
        // 进度条
        final ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(Double.MAX_VALUE);
        progressBar.setProgress(-1);
        vBox.getChildren().add(progressBar);
        uploadTaskVBox.getChildren().add(vBox);

        try {
            BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>> setHash = (hash, future1) -> {
                Platform.runLater(() -> {
                    vBox.setId(hash);
                    BiConsumer<Double, String> setProgress = (progress, transferred) -> {
                        Platform.runLater(() -> {
                            progressBar.setProgress(progress);
                            progresslabel.setText(transferred);
                        });
                    };
                    future1.complete(setProgress);
                });
            };
            future.complete(setHash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void addServerUploadTaskBar(String fileName, CompletableFuture<BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>>> future) {
        final VBox vBox = new VBox();
        vBox.setStyle("-fx-border-color: #8ac6ef;-fx-border-width: 2px;-fx-border-radius: 3px;");
        // 文件名
        Label fileNameLabel = new Label(fileName);
        vBox.getChildren().add(fileNameLabel);
        // Hash值
//        final Label hashLabel = new Label();
//        hashLabel.setText("");
//        vBox.getChildren().add(hashLabel);
        // 进度文字
        final Label progresslabel = new Label();
        progresslabel.setText("");
        vBox.getChildren().add(progresslabel);
        // 进度条
        final ProgressBar progressBar = new ProgressBar();
        progressBar.setPrefWidth(Double.MAX_VALUE);
        progressBar.setProgress(-1);
        vBox.getChildren().add(progressBar);
        uploadTaskVBox.getChildren().add(vBox);

        try {
            BiConsumer<String, CompletableFuture<BiConsumer<Double, String>>> setHash = (hash, future1) -> {
                Platform.runLater(() -> {
                    vBox.setId(hash);
                    BiConsumer<Double, String> setProgress = (progress, transferred) -> {
                        Platform.runLater(() -> {
                            progressBar.setProgress(progress);
                            progresslabel.setText(transferred);
                        });
                    };
                    future1.complete(setProgress);
                });
            };
            future.complete(setHash);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void openFileChooser() throws UnsupportedEncodingException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        fileChooser.getExtensionFilters().addAll();
        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            if ("server".equals(type)) {
                server.sendFile(file);
            } else {
                client.sendFile(file);
            }
        }
    }

    private String getFolderPath() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择保存文件路径");
        File path = directoryChooser.showDialog(new Stage());
        return path.getAbsolutePath();
    }
}