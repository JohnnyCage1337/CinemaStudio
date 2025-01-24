package com.example.io_app.API;

import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.DTO.CreatingFilmDTO;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CreatingFilmController {

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
        //int duration = Integer.parseInt(durationField.getText());

        // Walidacja tytułu
        if (title == null || title.trim().isEmpty()) {
            showAlert("Błąd", "Tytuł nie może być pusty!");
            return; // Zatrzymaj wykonywanie metody
        }

        // Walidacja gatunku
        if (genre == null || genre.trim().isEmpty()) {
            showAlert("Błąd", "Gatunek nie może być pusty!");
            return;
        }

        // Walidacja czasu trwania
        int duration;
        try {
            duration = Integer.parseInt(durationField.getText());
            if (duration <= 0) {
                showAlert("Błąd", "Czas trwania musi być większy niż 0!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Błąd", "Czas trwania musi być liczbą!");
            return;
        }

        // Tworzenie DTO i zapisywanie
        CreatingFilmDTO creatingFilmDTO = new CreatingFilmDTO(title, genre, duration);
        FilmService filmService = new FilmService();
        filmService.createAndSaveFilm(creatingFilmDTO);

        /*System.out.println("Dodano film: " + title + ", gatunek: " + genre + ", czas trwania: " + duration);

        for(Film film : filmRepository.findAll()){
            System.out.println(film);
        }*/

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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Tworzymy alert
        alert.setTitle(title); // Ustawiamy tytuł
        alert.setHeaderText(null); // Brak nagłówka
        alert.setContentText(message); // Ustawiamy treść wiadomości
        alert.showAndWait(); // Wyświetlamy alert
    }
}
