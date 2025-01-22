package com.example.io_app.API;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MoviesManagementController
{
    @FXML
    public void swtichToFilmShowings(ActionEvent event) {
        try {
            // Wczytanie pliku FXML poprzedniego widoku
            FXMLLoader loader = new FXMLLoader(
                    HelloApplication.class.getResource("/com/example/io_app/hello-view.fxml")
            );
            Parent root = loader.load();

            // Pobranie bieżącego Stage (okna)
            Stage stage = HelloApplication.getMainStage();

            // Ustawienie nowej sceny w oknie
            stage.setScene(new Scene(root));
            stage.setTitle("StudioCinema");


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openFormWindow() {
        try {
            // Wczytaj FXML dla formularza
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/io_app/Popout.fxml"));
            Parent root = loader.load();

            // Pobierz kontroler formularza i ustaw callback, jeśli potrzeba
            com.example.io_app.API.FormController formController = loader.getController();
            formController.setOnClose(() -> {
                // Możesz tu np. odświeżyć tabelę
                System.out.println("Zamknięto okno i można odświeżyć widok");
            });

            // Stwórz nowe okno (Stage)
            Stage stage =  new Stage();
            stage.setTitle("Dodaj nowy film");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL); // Okno modalne
            stage.initOwner(HelloApplication.getMainStage());
            stage.showAndWait(); // Poczekaj na zamknięcie okna
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
