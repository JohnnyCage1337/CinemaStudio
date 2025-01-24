module com.example.io_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.io_app to javafx.fxml;
    opens com.example.io_app.API to javafx.fxml;

    opens com.example.io_app.DOMAIN to javafx.base;
    opens com.example.io_app.DTO to javafx.base;

    exports com.example.io_app.API;
}