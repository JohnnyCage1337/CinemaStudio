package com.example.io_app.API.SessionWindows;

import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.APPLICATION.SessionService;
import com.example.io_app.DTO.Film.FilmDTO;
import com.example.io_app.DTO.Session.CreateSessionRequestDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class CreateSessionController {

    private FilmService filmService;
    private SessionService sessionService;

    @FXML    private ComboBox<FilmDTO> filmComboBox;
    @FXML    private DatePicker datePicker;
    @FXML    private TextField hourField;
    @FXML    private TextField roomField;
    @FXML    private TextField seatsField;
    @FXML    private TextField priceField;

    private Runnable onClose; // Callback po zamknięciu okna

    public CreateSessionController() {

        this.filmService = new FilmService();
        this.sessionService = new SessionService();
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    public void initialize() {

        // Pobierz listę FilmDTO z serwisu
        List<FilmDTO> filmList = filmService.getAllFilms();

        // Zamień na ObservableList
        ObservableList<FilmDTO> filmObservableList = FXCollections.observableArrayList(filmList);

        // Ustaw w ComboBox
        filmComboBox.setItems(filmObservableList);

        // Ustawiamy jak wyświetlać FilmDTO
        filmComboBox.setCellFactory(lv -> new ListCell<FilmDTO>() {
            @Override
            protected void updateItem(FilmDTO film, boolean empty) {
                super.updateItem(film, empty);
                if (empty || film == null) {
                    setText(null);
                } else {
                    // np. "1 Gladiator"
                    setText(film.getId() + " " + film.getTitle());
                }
            }
        });

        // Aby także na przycisku ComboBox (po wyborze) było to samo:
        filmComboBox.setButtonCell(filmComboBox.getCellFactory().call(null));
    }

    public void createSession(){

        // Odczytujemy wybrany obiekt
        FilmDTO selectedFilmDTO = filmComboBox.getValue();
        if (selectedFilmDTO == null) {
            showAlert("Błąd", "Musisz wybrać film!");
            return;
        }
        int filmId = selectedFilmDTO.getId();
        String filmTitle = selectedFilmDTO.getTitle();

        // Walidacja daty
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            showAlert("Błąd", "Musisz wybrać datę!");
            return;
        }

        // Walidacja godziny (np. "16:30")
        LocalTime localTime;
        try {
            localTime = LocalTime.parse(hourField.getText());
        } catch (Exception e) {
            showAlert("Błąd", "Niepoprawny format godziny. Użyj np. 16:30");
            return;
        }

        // Walidacja sali
        int roomNumber;
        try {
            roomNumber = Integer.parseInt(roomField.getText());
            if (roomNumber <= 0) {
                showAlert("Błąd", "Numer sali musi być dodatni!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Błąd", "Sala musi być liczbą!");
            return;
        }

        // Walidacja liczby miejsc
        int seats;
        try {
            seats = Integer.parseInt(seatsField.getText());
            if (seats <= 0) {
                showAlert("Błąd", "Liczba miejsc musi być > 0!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Błąd", "Liczba miejsc musi być liczbą całkowitą!");
            return;
        }

        // Walidacja ceny
        double price;
        try {
            price = Double.parseDouble(priceField.getText());
            if (price < 0) {
                showAlert("Błąd", "Cena nie może być ujemna!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Błąd", "Cena musi być liczbą!");
            return;
        }

        // DTO + wywołanie serwisu
        CreateSessionRequestDTO requestDTO = new CreateSessionRequestDTO(
                filmId,
                localDate,
                localTime,
                roomNumber,
                seats,
                price
        );
        //sessionService.createSessionUseCase(...);


        // odświeżenie widok w oknie-rodzicu
        if (onClose != null) {
            onClose.run();
        }

        // zamknięcie okna:
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) filmComboBox.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Tworzymy alert
        alert.setTitle(title); // Ustawiamy tytuł
        alert.setHeaderText(null); // Brak nagłówka
        alert.setContentText(message); // Ustawiamy treść wiadomości
        alert.showAndWait(); // Wyświetlamy alert
    }
}
