package com.example.io_app.API.MainSites;

import com.example.io_app.API.Application;
import com.example.io_app.API.FilmWindows.UpdateFilmController;
import com.example.io_app.API.SessionWindows.CreateSessionController;
import com.example.io_app.API.SessionWindows.UpdateSessionController;
import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.APPLICATION.SessionService;
import com.example.io_app.DTO.Film.DeleteFilmRequestDTO;
import com.example.io_app.DTO.Film.FilmDTO;
import com.example.io_app.DTO.Film.FindFilmRequestDTO;
import com.example.io_app.DTO.Film.UpdateFilmRequestDTO;
import com.example.io_app.DTO.Session.DeleteSessionRequestDTO;
import com.example.io_app.DTO.Session.SessionDTO;
import com.example.io_app.DTO.Session.UpdateSession.UpdateSessionRequestDTO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SessionSiteController implements Initializable {

    private SessionService sessionService;


    @FXML
    private DatePicker datePicker;

    @FXML private TableView<SessionDTO> sessionTableView;

    @FXML private TableColumn<SessionDTO, Integer> idSessionColumn;
    @FXML private TableColumn<SessionDTO, String> titleColumn;
    @FXML private TableColumn<SessionDTO, String> dateColumn;
    @FXML private TableColumn<SessionDTO, String> startTimeColumn;
    @FXML private TableColumn<SessionDTO, String> endTimeColumn;
    @FXML private TableColumn<SessionDTO, Integer> placeColumn;
    @FXML private TableColumn<SessionDTO, Integer> availableSeatsColumn;
    @FXML private TableColumn<SessionDTO, Integer> allSeatsColumn;
    @FXML private TableColumn<SessionDTO, Double> priceColumn;

    boolean isLoadedAll;
    private FilmService filmService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filmService = new FilmService();
        datePicker.setValue(LocalDate.now());


        sessionService = new SessionService();
        idSessionColumn.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("filmTitle"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        allSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("allSeats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Załaduj dane do TableView
        loadSessionData(datePicker.getValue());

        isLoadedAll = false;


    }

    private void loadSessionData() {

        List<SessionDTO> sessions = sessionService.getAllSessions();

        ObservableList<SessionDTO> observableSessions = FXCollections.observableArrayList(sessions);
        sessionTableView.setItems(observableSessions);
    }

    public void loadSessionData(LocalDate data){

        List<SessionDTO> sessions = sessionService.getSessionByDate(data);
        ObservableList<SessionDTO> observableSessions = FXCollections.observableArrayList(sessions);
        sessionTableView.setItems(observableSessions);
    }

    @FXML
    public void handleSwitchToFilmsButton(ActionEvent event) {
        try {
            // Wczytanie nowego pliku FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/io_app/MainSites/FilmSite.fxml"));
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
    public void handleCreateButton() {
        try {
            // Wczytaj FXML dla formularza tworzenia seansu
            FXMLLoader loader = new FXMLLoader(
                    Application.class.getResource("/com/example/io_app/SessionWindows/CreateSession.fxml")
            );
            Parent root = loader.load();

            // Pobierz kontroler, np. CreateSessionController
            CreateSessionController createSessionController = loader.getController();
            // Jeśli chcesz odświeżyć tabelę po zamknięciu okna, ustaw callback
            createSessionController.setOnClose(() -> {
                if(isLoadedAll) {
                    loadSessionData();
                }
                else{
                    loadSessionData(datePicker.getValue());
                }
            });

            // Stwórz nowe okno (Stage)
            Stage stage = new Stage();
            stage.setTitle("Dodaj nowy seans");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL); // Okno modalne
            stage.initOwner(Application.getMainStage());
            stage.showAndWait(); // Poczekaj na zamknięcie okna
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDeleteButton(){

        SessionDTO selectedSession = sessionTableView.getSelectionModel().getSelectedItem();

        // Obsługa braku zaznaczenia filmu
        if (selectedSession == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("Brak zaznaczenia");
            noSelectionAlert.setHeaderText("Nie wybrano żadnego seansu do usunięcia");
            noSelectionAlert.setContentText("Zaznacz seans na liście, zanim klikniesz 'Usuń'.");
            noSelectionAlert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Potwierdź usunięcie");
        confirmationAlert.setHeaderText("Czy na pewno chcesz usunąć wybrany seans?");

        DeleteSessionRequestDTO requestDTO = new DeleteSessionRequestDTO(selectedSession);

        // Czekanie na odpowiedź użytkownika
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Potwierdzenie usunięcia przez uzytkownika
            boolean success = sessionService.deleteSessionUseCase(requestDTO).isResult();
            if (success) {
                // Jeśli usunięcie się powiodło, odświeżamy dane w tabeli
                if(isLoadedAll) {
                    loadSessionData();
                }
                else{
                    loadSessionData(datePicker.getValue());
                }
            } else {
                System.out.println("Nie udało się usunąć wybranego seansu");
            }
        } else {
            // Użytkownik anulował usunięcie
            System.out.println("Usunięcie seansu anulowane.");
        }
    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        // Zamyka aplikację
        Platform.exit();
    }
    @FXML
    public void handleAllSessions(){
        loadSessionData();
        if(datePicker.getValue() != null) {
            datePicker.setValue(null);
        }
    }

    @FXML
    public void handleSessionsByDate(){
        if(datePicker.getValue() != null) {
        loadSessionData(datePicker.getValue());
        }
    }
    @FXML
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void updateSessionButton(){
        try {
            // Pobierz zaznaczony film
            var selectedSession = sessionTableView.getSelectionModel().getSelectedItem();
            if (selectedSession == null) {
                showAlert("Błąd", "Nie wybrano żadnego seansu");
                return;
            }

            // Wczytaj FXML dla formularza
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/io_app/SessionWindows/UpdateSession.fxml"));
            Parent root = loader.load();


            int id = filmService.findFilmUseCase(new FindFilmRequestDTO(selectedSession.getFilmTitle())).getFoundFilm().getId();

            var dto = new UpdateSessionRequestDTO(
                    selectedSession.getSessionId(),
                    id,
                    selectedSession.getDate(),
                    selectedSession.getStartTime(),
                    selectedSession.getEndTime(),
                    selectedSession.getPlace(),
                    selectedSession.getAvailableSeats(),
                    selectedSession.getPrice());

            // Pobierz kontroler formularza i ustaw film
            UpdateSessionController controller = loader.getController();
            controller.setDto(dto); // Przekaż obiekt seansu do edycji
            controller.setOnClose(() -> {
                if(isLoadedAll) {
                    loadSessionData();
                }
                else{
                    loadSessionData(datePicker.getValue());
                }
            });

            // Stwórz nowe okno (Stage)
            Stage stage = new Stage();
            stage.setTitle("Modyfikuj seans");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL); // Okno modalne
            stage.initOwner(Application.getMainStage());
            stage.showAndWait(); // Poczekaj na zamknięcie okna
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
