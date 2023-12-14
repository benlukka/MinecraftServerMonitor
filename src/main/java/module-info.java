module com.benlukka.minecraftservermonitor {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires org.json;
    requires commons.io;
    requires java.desktop;
    opens com.benlukka.minecraftservermonitor to javafx.fxml;
    exports com.benlukka.minecraftservermonitor;
}