package com.example.io_app.API;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        // Ładujemy plik FXML (zmień ścieżkę, jeśli masz inny układ katalogów)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/io_app/hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("FilmSchedule");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
