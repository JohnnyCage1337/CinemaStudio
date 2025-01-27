package com.example.io_app.API.SessionWindows;

import com.example.io_app.API.MainSites.FilmSiteController;
import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.APPLICATION.SessionService;
import com.example.io_app.DOMAIN.Film.FilmValidator;
import com.example.io_app.DTO.Session.CreatingSession.CreateSessionRequestDTO;
import com.example.io_app.DTO.Session.CreatingSession.availableTimeSlotsDueDateRequestDTO;
import com.example.io_app.DTO.Session.CreatingSession.getFilmDetailsRequestDTO;
import com.example.io_app.DTO.Session.UpdateSession.UpdateSessionRequestDTO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;

public class UpdateSessionController {
    private SessionService sessionService;
    private FilmService filmService;

    @FXML
    private TextField filmIdField;
    @FXML private TextField filmTitleField;
    @FXML private DatePicker datePickerField;
    @FXML private ComboBox<String> timeBeginComboBox;
    @FXML private TextField timeEndField;
    @FXML private ComboBox<Integer> roomComboBox;
    @FXML private TextField numberOfSeatsField;
    @FXML private TextField priceField;

    private Runnable onClose;
    private int filmDuration;
    private HashMap<Integer, Integer> hallCapacityMap = new HashMap<>();

    private UpdateSessionRequestDTO dto;

    public void setDto(UpdateSessionRequestDTO dto) {
        this.dto = dto;
        filmIdField.setText(String.valueOf(dto.getFilmID()));
        filmTitleField.setText("");
        datePickerField.setValue(dto.getDate());
        timeBeginComboBox.setValue(dto.getStartTime().toString());
        timeEndField.setText(String.valueOf(dto.getEndTime()));
        roomComboBox.setValue(dto.getRoomNumber());
        numberOfSeatsField.setText(String.valueOf(dto.getTotalSeats()));
        priceField.setText(String.valueOf(dto.getPrice()));

        ;}

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


        filmService = new FilmService();
        sessionService = new SessionService();

    }



    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    public void displayFilmDataBaseButton() {
        try {
            // Załaduj plik FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/io_app/SessionWindows/FilmSiteCreateSessionCase.fxml"));

            // Ustaw kontroler, jeśli wymagane (opcjonalnie)
            FilmSiteController controller = loader.getController();

            // Załaduj widok
            Parent root = loader.load();

            // Stwórz nowe okno
            Stage stage = new Stage();
            stage.setTitle("Film Database");
            stage.setScene(new Scene(root));

            // Pokaż nowe okno
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @FXML
    public void findFilmByEnetredId() {
        try {
            int id = Integer.parseInt(filmIdField.getText());
            FilmValidator.validateId(id);

            // Wywołanie serwisu
            var dto = filmService.findFilmByIdUseCase(new getFilmDetailsRequestDTO(id));

            // Ustawienie pól widoku
            filmTitleField.setText(dto.getFilmTitle());
            filmDuration = dto.getFilmDuration();

            // Odblokowanie wyboru sali
            roomComboBox.setDisable(false);
            roomComboBox.setValue(null);
            // Zablokowanie pola ID
            filmIdField.setDisable(true);

        } catch (NumberFormatException e) {
            showAlert("Błąd", "Id jest liczbą.");
        } catch (IllegalArgumentException e) {
            showAlert("Błąd", e.getMessage());
        }
    }



    @FXML
    public void onRoomSelected() {
        timeBeginComboBox.setDisable(true);
        timeBeginComboBox.setValue(null);
        datePickerField.setValue(null);
        Integer selectedRoom = roomComboBox.getValue();
        if (selectedRoom == null) {
            return;
        }

        // Wyświetl od razu liczbę miejsc (opcjonalne)
        int numberOfSeats = hallCapacityMap.getOrDefault(selectedRoom, 0);
        numberOfSeatsField.setText(String.valueOf(numberOfSeats));

        // Odblokuj wybór daty
        datePickerField.setDisable(false);

        // Zablokuj timeBeginComboBox, jeśli użytkownik zmienia salę w locie
        timeBeginComboBox.setDisable(true);
        timeBeginComboBox.getItems().clear();
        timeEndField.clear();
    }

    /**
     * Wywoływane po wybraniu daty – pobiera wolne sloty w wybranej sali i dacie.
     */
    @FXML
    public void getAvailableTimeSlotsDueDate() {
        LocalDate date = datePickerField.getValue();
        if (date == null) return;

        Integer roomNumber = roomComboBox.getValue();
        if (roomNumber == null) return;

        // Pobierz wolne sloty dla danej daty i sali
        var availableHours = sessionService.getAvailableTimeSlotsDueDate(
                new availableTimeSlotsDueDateRequestDTO(date),
                roomNumber // NOWY parametr
        );

        // Wyczyść ComboBox
        timeBeginComboBox.getItems().clear();

        // Dodaj do ComboBox tylko sloty pasujące do czasu trwania filmu,
        // jeśli chcesz filtrować już tutaj. Możesz też filtrować w serwisie:
        availableHours.getOpenTimeSlots()
                .stream()
                .filter(timeSlot -> isSlotAvailableForFilm(timeSlot, availableHours.getOpenTimeSlots(), filmDuration))
                .forEach(filteredSlot -> timeBeginComboBox.getItems().add(filteredSlot.toString()));

        // Odblokuj wybór godziny
        timeBeginComboBox.setDisable(false);

        // Zablokuj timeEndField do momentu wyboru godziny
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

        // Możesz odblokować pole priceField dopiero po wybraniu godziny:
        priceField.setDisable(false);
    }

    @FXML
    public void updateSession() {
        int filmId = Integer.parseInt(filmIdField.getText());
        LocalDate sessionDate = datePickerField.getValue();
        LocalTime timeBegin = LocalTime.parse(timeBeginComboBox.getValue());
        LocalTime timeEnd = LocalTime.parse(timeEndField.getText());
        int roomNumber = roomComboBox.getValue();
        int numberOfSeats = Integer.parseInt(numberOfSeatsField.getText());
        double price = Double.parseDouble(priceField.getText());

        var requestDTO = new UpdateSessionRequestDTO(dto.getSessionId(),filmId, sessionDate, timeBegin, timeEnd,roomNumber,numberOfSeats,price);

        sessionService.updateSessionUseCase(requestDTO);

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
        filmIdField.setText(String.valueOf(dto.getFilmID()));
        filmTitleField.setText("");
        datePickerField.setValue(dto.getDate());
        timeBeginComboBox.setValue(dto.getStartTime().toString());
        timeEndField.setText(String.valueOf(dto.getEndTime()));
        roomComboBox.setValue(dto.getRoomNumber());
        numberOfSeatsField.setText(String.valueOf(dto.getTotalSeats()));
        priceField.setText(String.valueOf(dto.getPrice()));
    }

}
