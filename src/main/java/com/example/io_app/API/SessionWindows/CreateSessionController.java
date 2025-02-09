package com.example.io_app.API.SessionWindows;

import com.example.io_app.API.MainSites.FilmSiteController;
import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.APPLICATION.SessionService;
import com.example.io_app.DTO.Session.CreateSession.CreateSessionRequestDTO;
import com.example.io_app.DTO.Session.CreateSession.availableTimeSlotsDueDateRequestDTO;
import com.example.io_app.DTO.Session.CreateSession.getFilmDetailsRequestDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class CreateSessionController {

    private FilmService filmService;
    private SessionService sessionService;

    @FXML private TextField filmIdField;
    @FXML private TextField filmTitleField;

    @FXML private DatePicker datePickerField;
    @FXML private ComboBox<String> timeBeginComboBox;
    @FXML private TextField timeEndField;

    @FXML private ComboBox<Integer> roomComboBox;
    @FXML private TextField numberOfSeatsField;
    @FXML private TextField priceField;

    private int filmDuration;
    private HashMap<Integer, Integer> hallCapacityMap = new HashMap<>();

    private Runnable onClose; // Callback po zamknięciu okna

    public CreateSessionController() {
        this.filmService = new FilmService();
        this.sessionService = new SessionService();
    }

    @FXML
    public void initialize() {
        // Mapowanie sal -> liczba miejsc
        hallCapacityMap.put(1, 30);
        hallCapacityMap.put(2, 40);
        hallCapacityMap.put(3, 50);

        // Wypełnienie roomComboBox
        roomComboBox.getItems().addAll(hallCapacityMap.keySet());

        // Zablokuj wszystko na starcie, poza filmIdField
        filmTitleField.setDisable(true);
        datePickerField.setDisable(true);
        timeBeginComboBox.setDisable(true);
        timeEndField.setDisable(true);
        roomComboBox.setDisable(true);
        numberOfSeatsField.setDisable(true);
        priceField.setDisable(true);
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    public void displayFilmDataBaseButton() {
        try {
            // Ładowanie pliku FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/io_app/SessionWindows/FilmSiteCreateSessionCase.fxml"));

            // Ustawienie kontrolera
            FilmSiteController controller = loader.getController();

            // Załadowanie widoku
            Parent root = loader.load();

            // Stworzenie nowego okna
            Stage stage = new Stage();
            stage.setTitle("Film Database");
            stage.setScene(new Scene(root));

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void findFilmByEnetredId() {
        try {
            int id = Integer.parseInt(filmIdField.getText());
            var dto = filmService.findFilmByIdUseCase(new getFilmDetailsRequestDTO(id));
            filmTitleField.setText(dto.getFilmTitle());
            filmDuration = dto.getFilmDuration();

            roomComboBox.setDisable(false);

            filmIdField.setDisable(true);

        } catch (NumberFormatException e) {
            showAlert("Błąd", "Id jest liczbą.");
        } catch (IllegalArgumentException e) {
            showAlert("Błąd", e.getMessage());
        }
    }

    @FXML
    public void onRoomSelected() {
        datePickerField.setValue(null);
        Integer selectedRoom = roomComboBox.getValue();
        if (selectedRoom == null) {
            return;
        }

        int numberOfSeats = hallCapacityMap.getOrDefault(selectedRoom, 0);
        numberOfSeatsField.setText(String.valueOf(numberOfSeats));

        datePickerField.setDisable(false);

        timeBeginComboBox.setDisable(true);
        timeBeginComboBox.getItems().clear();
        timeEndField.clear();
    }

    @FXML
    public void getAvailableTimeSlotsDueDate() {
        LocalDate date = datePickerField.getValue();
        if (date == null) return;

        Integer roomNumber = roomComboBox.getValue();
        if (roomNumber == null) return;

        // Pobranie wolnych slotów dla danej daty i sali
        var availableHours = sessionService.getAvailableTimeSlotsDueDate(
                new availableTimeSlotsDueDateRequestDTO(date),
                roomNumber // NOWY parametr
        );

        // Czyszczenie ComboBoxa
        timeBeginComboBox.getItems().clear();

        // Dodanie do ComboBoxa tylko sloty pasujących do czasu trwania filmu,
        availableHours.getOpenTimeSlots()
                .stream()
                .filter(timeSlot -> isSlotAvailableForFilm(timeSlot, availableHours.getOpenTimeSlots(), filmDuration))
                .forEach(filteredSlot -> timeBeginComboBox.getItems().add(filteredSlot.toString()));

        timeBeginComboBox.setDisable(false);

        timeEndField.clear();
    }

    private boolean isSlotAvailableForFilm(LocalTime startTime, List<LocalTime> timeSlots, int filmDuration) {
        LocalTime filmEndTime = startTime.plusMinutes(filmDuration);
        return timeSlots.contains(filmEndTime) || timeSlots.stream()
                .anyMatch(slot -> !slot.isBefore(startTime) && !slot.isAfter(filmEndTime));
    }

    @FXML
    private void calculateFinishedTime() {
        String selectedTime = timeBeginComboBox.getValue();
        if (selectedTime == null) return;

        LocalTime startTime = LocalTime.parse(selectedTime);
        LocalTime endTime = startTime.plusMinutes(filmDuration);

        timeEndField.setText(endTime.toString());

        priceField.setDisable(false);
    }

    @FXML
    public void createSession() {
        int filmId = Integer.parseInt(filmIdField.getText());
        LocalDate sessionDate = datePickerField.getValue();
        LocalTime timeBegin = LocalTime.parse(timeBeginComboBox.getValue());
        LocalTime timeEnd = LocalTime.parse(timeEndField.getText());
        int roomNumber = roomComboBox.getValue();
        int numberOfSeats = Integer.parseInt(numberOfSeatsField.getText());
        double price = Double.parseDouble(priceField.getText());

        var responseDTO = sessionService.createSessionUseCase(
                new CreateSessionRequestDTO(filmId, sessionDate, timeBegin, timeEnd,
                        roomNumber, numberOfSeats, price)
        );

        if (onClose != null) {
            onClose.run();
        }
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) filmIdField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void resetFields(){

        // Zablokowanie pól
        filmTitleField.setDisable(true);
        datePickerField.setDisable(true);
        timeBeginComboBox.setDisable(true);
        timeEndField.setDisable(true);
        roomComboBox.setDisable(true);
        numberOfSeatsField.setDisable(true);
        priceField.setDisable(true);

        // Odblokowanie pola ID filmu
        filmIdField.setDisable(false);

        // Czyszczenie pól
        filmTitleField.clear();
        datePickerField.setValue(null);
        timeBeginComboBox.getItems().clear();
        timeBeginComboBox.setValue(null);
        timeEndField.clear();

        roomComboBox.setValue(null);
        numberOfSeatsField.clear();
        priceField.clear();

        // Czyszczenie pola ID filmu
        filmIdField.clear();
    }
}
