module com.xiaqi.filetransfer {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.jfr;
    requires netty.shaded;
    requires com.google.protobuf;

    opens com.xiaqi.filetransfer.gui.start to javafx.fxml;
    opens com.xiaqi.filetransfer.gui.main to javafx.fxml;
    exports com.xiaqi.filetransfer.gui.start;
    exports com.xiaqi.filetransfer.gui.main;
}