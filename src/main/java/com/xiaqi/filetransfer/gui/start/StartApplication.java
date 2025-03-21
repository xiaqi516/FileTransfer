package com.xiaqi.filetransfer.gui.start;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class StartApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("start-view.fxml"));
        Image image = new Image(getClass().getResourceAsStream("/icon.jpeg"));

        Scene scene = new Scene(fxmlLoader.load(), 500, 300);
        stage.setTitle("FileTransfer");
        stage.getIcons().add(image);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
