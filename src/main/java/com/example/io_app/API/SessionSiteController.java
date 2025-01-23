package com.example.io_app.API;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;

public class SessionSiteController {

    @FXML
    public void switchToFilmsManagement(ActionEvent event) {
        try {
            // Wczytanie nowego pliku FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/io_app/FilmSite.fxml"));
            Parent root = fxmlLoader.load();

            // Pobranie bieżącego okna
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Ustawienie nowej sceny w bieżącym oknie
            stage.setScene(new Scene(root));
            stage.setTitle("Films");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        // Zamyka aplikację
        Platform.exit();
    }
}
