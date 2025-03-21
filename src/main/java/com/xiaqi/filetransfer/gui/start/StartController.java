package com.xiaqi.filetransfer.gui.start;

import com.xiaqi.filetransfer.gui.main.MainApplication;
import com.xiaqi.filetransfer.gui.main.MainStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {
    private void onButtonActive(ActionEvent event, String type) {
        Button clientButton = (Button) event.getSource();
        Stage startStage = (Stage) clientButton.getScene().getWindow();
        startStage.close();

        MainApplication mainApplication = new MainApplication();
        try {
            mainApplication.start(new MainStage(type));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onClientButtonActive(ActionEvent event) {
        onButtonActive(event, "client");
    }

    @FXML
    private void onServerButtonActive(ActionEvent event) {
        onButtonActive(event, "server");
    }
}
