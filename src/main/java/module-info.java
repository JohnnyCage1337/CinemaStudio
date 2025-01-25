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

    exports com.example.io_app.API;
    opens com.example.io_app.DTO.Film to javafx.base;
    exports com.example.io_app.API.FilmWindows;
    opens com.example.io_app.API.FilmWindows to javafx.fxml;
    exports com.example.io_app.API.MainSites;
    opens com.example.io_app.API.MainSites to javafx.fxml;
    opens com.example.io_app.DOMAIN.Film to javafx.base;
    opens com.example.io_app.DOMAIN.Session to javafx.base;
    opens com.example.io_app.API.SessionWindows to javafx.fxml;
}