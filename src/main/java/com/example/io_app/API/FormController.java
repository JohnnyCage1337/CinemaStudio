package com.example.io_app.API;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FormController {

    @FXML
    private TextField titleField;
    @FXML
    private TextField genreField;
    @FXML
    private TextField durationField;

    private Runnable onClose; // Callback po zamknięciu okna

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    public void saveMovie() {
        String title = titleField.getText();
        String genre = genreField.getText();
        int duration = Integer.parseInt(durationField.getText());

        // Tu możesz dodać logikę zapisywania filmu (np. do bazy danych lub listy)
        System.out.println("Dodano film: " + title + ", gatunek: " + genre + ", czas trwania: " + duration);

        if (onClose != null) {
            onClose.run(); // Wywołaj callback, np. odświeżenie tabeli
        }
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow(); // Pobierz aktualne okno
        stage.close(); // Zamknij okno
    }


}
