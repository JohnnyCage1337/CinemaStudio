package com.example.io_app.API.FilmWindows;

import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.DTO.Film.CreateFilmRequestDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CreateFilmController {

    private FilmService filmService;

    @FXML
    private TextField titleField;
    @FXML
    private ComboBox<String> genreComboBox;
    @FXML
    private TextField durationField;

    public CreateFilmController(){
        filmService = new FilmService();
    }

    private Runnable onClose; // Callback po zamknięciu okna

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    public void initialize(){
        genreComboBox.getItems().addAll(
                "Akcja",
                "Komedia",
                "Horror",
                "Thriller",
                "Dramat",
                "Animacja"
        );
    }

    @FXML
    public void createFilm() {
        String title = titleField.getText();
        String genre = genreComboBox.getValue();

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
        } catch (NumberFormatException e) {
            showAlert("Błąd", "Czas trwania musi być liczbą!");
            return;
        }

        // Tworzenie DTO + wywołanie serwisu
        CreateFilmRequestDTO createFilmRequestDTO = new CreateFilmRequestDTO(title, genre, duration);
        try {
            filmService.createFilmUseCase(createFilmRequestDTO);
            if (onClose != null) {
                onClose.run();
            }
            closeWindow();
        } catch (IllegalArgumentException e) {
            showAlert("Błąd", e.getMessage());
        }
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
