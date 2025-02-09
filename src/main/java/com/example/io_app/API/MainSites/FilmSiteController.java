package com.example.io_app.API.MainSites;

import com.example.io_app.API.Application;
import com.example.io_app.API.FilmWindows.CreateFilmController;
import com.example.io_app.API.FilmWindows.FindFilmController;
import com.example.io_app.API.FilmWindows.UpdateFilmController;
import com.example.io_app.APPLICATION.FilmService;
import com.example.io_app.DTO.Film.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FilmSiteController implements Initializable {

    private FilmService filmService;

    @FXML    private TableView<FilmDTO> filmTableView;
    @FXML    private TableColumn<FilmDTO, Integer> idFilmColumn;
    @FXML    private TableColumn<FilmDTO, String> titleColumn;
    @FXML    private TableColumn<FilmDTO, String> genreColumn;
    @FXML    private TableColumn<FilmDTO, Integer> durationColumn;

    private Runnable onClose;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filmService = new FilmService();

        idFilmColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        loadFilmData();
    }

    private void loadFilmData() {

        List<FilmDTO> films = filmService.getAllFilms();

        ObservableList<FilmDTO> observableFilms = FXCollections.observableArrayList(films);
        filmTableView.setItems(observableFilms);
    }

    @FXML
    public void handleSwitchToFilmsButton(ActionEvent event) {
        try {
            // Wczytanie pliku FXML poprzedniego widoku
            FXMLLoader loader = new FXMLLoader(
                    Application.class.getResource("/com/example/io_app/MainSites/SessionSite.fxml")
            );
            Parent root = loader.load();

            // Pobranie bieżącego Stage (okna)
            Stage stage = Application.getMainStage();

            // Ustawienie nowej sceny w oknie
            stage.setScene(new Scene(root));
            stage.setTitle("Sessions");


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCreateButton() {
        try {
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/io_app/FilmWindows/CreateFilm.fxml"));
            Parent root = loader.load();

            CreateFilmController createFilmController = loader.getController();
            createFilmController.setOnClose(() -> {
                loadFilmData();
                /*System.out.println("Zamknięto okno i można odświeżyć widok");*/
            });

            Stage stage = new Stage();
            stage.setTitle("Dodaj nowy film");
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

        FilmDTO selectedFilm = filmTableView.getSelectionModel().getSelectedItem();

        // Obsługa braku zaznaczenia filmu
        if (selectedFilm == null) {
          showAlert("Błąd", "Niezaznaczono filmu");
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Potwierdź usunięcie");
        confirmationAlert.setHeaderText("Czy na pewno chcesz usunąć wybrany film?");
        confirmationAlert.setContentText("Film: " + selectedFilm.getTitle());

        DeleteFilmRequestDTO requestDTO = new DeleteFilmRequestDTO(selectedFilm);

        // Czekanie na odpowiedź użytkownika
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Potwierdzenie usunięcia przez uzytkownika
            boolean success = filmService.deleteFilmUseCase(requestDTO).isResult();
            if (success) {
                // Jeśli usunięcie się powiodło, odświeżamy dane w tabeli
                loadFilmData();
            }else {
                showAlert("Błąd", "Nie udalo sie usunąć filmu.");
            }
        }
    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        // Zamyka aplikację
        Platform.exit();
    }

    @FXML
    public void handleFindButton() {
        try {
            // Wczytaj FXML dla formularza
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/io_app/FilmWindows/FindFilm.fxml"));
            Parent root = loader.load();

            // Pobranie kontrolera formularza i ew. ustawienie callback, jeśli potrzeba
            FindFilmController findFilmController = loader.getController();
            findFilmController.setOnClose(() -> {
                loadFilmData();
            });

            //przekazanie aktualnego kontrolera (strony głównej filmów) od dziecka - kontroler "Znajdź film"
            findFilmController.setFilmSiteController(this);

            Stage stage = new Stage();
            stage.setTitle("Znajdź film");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL); // Okno modalne
            stage.initOwner(Application.getMainStage());
            stage.showAndWait(); // Poczekaj na zamknięcie okna
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUpdateButton() {
        try {
            FilmDTO selectedFilm = filmTableView.getSelectionModel().getSelectedItem();
            if (selectedFilm == null) {
                showAlert("Błąd", "Nie zaznaczono filmu.");
                return;
            }

            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/io_app/FilmWindows/UpdateFilm.fxml"));
            Parent root = loader.load();

            //przekazujemy dane filmu do UpdateFilmController
            var dto = new UpdateFilmRequestDTO(
                    selectedFilm.getId(),
                    selectedFilm.getTitle(),
                    selectedFilm.getTitle(),
                    selectedFilm.getGenre(),
                    selectedFilm.getDuration());

            // Pobierz kontroler formularza i ustaw film
            UpdateFilmController controller = loader.getController();
            controller.setFilm(dto); // Przekaż obiekt filmu do edycji
            controller.setOnClose(() -> {
                loadFilmData(); // Odśwież widok po zamknięciu okna
            });

            Stage stage = new Stage();
            stage.setTitle("Modyfikuj film");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL); // Okno modalne
            stage.initOwner(Application.getMainStage());
            stage.showAndWait(); // Poczekaj na zamknięcie okna
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processFindFilm(String filmTitleFragment) {

        FindFilmResponseDTO responseDTO = filmService.findFilmUseCase(new FindFilmRequestDTO(filmTitleFragment));

        List<FilmDTO> foundFilms = responseDTO.getFoundFilms();

        if (!foundFilms.isEmpty()) {
            // utworzenie listy z obiektami, jeśli znaleziono pasujące
            ObservableList<FilmDTO> foundFilmList = FXCollections.observableArrayList(foundFilms);
            filmTableView.setItems(foundFilmList);
        } else {
            showAlert("Brak wyników", "Nie znaleziono filmu o tytule: " + filmTitleFragment);
        }
    }

    public void handleAllFilmsButton(){
        loadFilmData();
    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // Tworzymy alert
        alert.setTitle(title); // Ustawiamy tytuł
        alert.setHeaderText(null); // Brak nagłówka
        alert.setContentText(message); // Ustawiamy treść wiadomości
        alert.showAndWait(); // Wyświetlamy alert
    }

}