package com.xiaqi.filetransfer.gui.main;

import javafx.stage.Stage;

public class MainStage extends Stage {
    private String type;

    public MainStage(String type) {
        super();
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
