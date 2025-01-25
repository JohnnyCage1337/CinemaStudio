package com.example.io_app.API.MainSites;

import com.example.io_app.API.Application;
import com.example.io_app.API.SessionWindows.CreateSessionController;
import com.example.io_app.APPLICATION.SessionService;
import com.example.io_app.DTO.Session.SessionDTO;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SessionSiteController implements Initializable {

    private SessionService sessionService;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sessionService = new SessionService();

        idSessionColumn.setCellValueFactory(new PropertyValueFactory<>("sessionId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        placeColumn.setCellValueFactory(new PropertyValueFactory<>("place"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("availableSeats"));
        allSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("allSeats"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Załaduj dane do TableView
        loadSessionData();

    }

    private void loadSessionData() {

        List<SessionDTO> sessions = sessionService.getAllSessions();

        ObservableList<SessionDTO> observableSessions = FXCollections.observableArrayList(sessions);
        sessionTableView.setItems(observableSessions);
    }

    @FXML
    public void handleSwitchToSessionsButton(ActionEvent event) {
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
                loadSessionData();
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
    public void handleExitButton(ActionEvent event) {
        // Zamyka aplikację
        Platform.exit();
    }


}
