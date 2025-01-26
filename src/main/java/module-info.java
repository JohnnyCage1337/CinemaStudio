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

    exports com.example.io_app.API;
    exports com.example.io_app.API.FilmWindows;
    exports com.example.io_app.API.MainSites;
    exports com.example.io_app.API.SessionWindows;
    exports com.example.io_app.APPLICATION;
    exports com.example.io_app.DTO.Film;
    exports com.example.io_app.DTO.Session;
    exports com.example.io_app.INFRASTRUCTURE;
    exports com.example.io_app.DOMAIN.Film;
    exports com.example.io_app.DOMAIN.Session;

    opens com.example.io_app to javafx.fxml;
    opens com.example.io_app.API to javafx.fxml;
    opens com.example.io_app.API.SessionWindows to javafx.fxml;
    opens com.example.io_app.API.FilmWindows to javafx.fxml;
    opens com.example.io_app.API.MainSites to javafx.fxml;
    opens com.example.io_app.APPLICATION to javafx.fxml;
    opens com.example.io_app.DOMAIN.Film to javafx.base;
    opens com.example.io_app.DOMAIN.Session to javafx.base;
    opens com.example.io_app.DTO.Session to javafx.base, javafx.fxml;
    opens com.example.io_app.DTO.Film to javafx.base, javafx.fxml;

}