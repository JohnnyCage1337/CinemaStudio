package com.example.io_app.API.FilmWindows;

import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.DTO.Film.UpdateFilmRequestDTO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateFilmController {

    private FilmService filmService;

    @FXML    private TextField titleField;
    @FXML    private TextField durationField;
    @FXML    private ComboBox<String> genreComboBox; // ComboBox zamiast TextField

    private UpdateFilmRequestDTO requestDTO;  // Obiekt (dto) filmu do edycji
    private Runnable onClose;             // Callback po zamknięciu okna

    /**
     * Metoda inicjalizująca (wywoływana automatycznie po załadowaniu FXML),
     * w której możemy ustawić listę dostępnych gatunków.
     */
    @FXML
    public void initialize() {

        filmService = new FilmService();

        // Dodaj przykładowe gatunki (lub pobierz z bazy, jeśli potrzebne)
        genreComboBox.getItems().addAll(
                "Akcja",
                "Komedia",
                "Horror",
                "Thriller",
                "Dramat",
                "Animacja"
        );
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    /**
     * Metoda do ustawienia danych filmu w kontrolerze (z DTO).
     * Wywoływana np. bezpośrednio po otwarciu okna.
     */
    public void setFilm(UpdateFilmRequestDTO film) {
        this.requestDTO = film;

        // Ustaw wartości w polach wejściowych
        titleField.setText(film.getNewFilmTitle());
        durationField.setText(String.valueOf(film.getNewDuration()));

        // Ustaw wartość gatunku w ComboBox
        genreComboBox.setValue(film.getNewFilmGenre());
    }

    @FXML
    public void updateFilm() {
        // Odczyt i walidacja tytułu
        String title = titleField.getText();
        if (title == null || title.trim().isEmpty()) {
            showAlert("Błąd", "Tytuł nie może być pusty!");
            return;
        }

        // Odczyt i walidacja gatunku z ComboBox
        String selectedGenre = genreComboBox.getValue();
        if (selectedGenre == null || selectedGenre.trim().isEmpty()) {
            showAlert("Błąd", "Musisz wybrać gatunek!");
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

        // Ustawienie nowych wartości w DTO
        requestDTO.setNewFilmTitle(title);
        requestDTO.setNewFilmGenre(selectedGenre);
        requestDTO.setNewDuration(duration);

        // Zapisanie zmodyfikowanego filmu przez serwis
        filmService.updateFilmUseCase(requestDTO);

        // Jeśli przekazano callback (np. odświeżenie listy w głównym oknie), wywołaj go
        if (onClose != null) {
            onClose.run();
        }

        // Zamknij to okno
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}