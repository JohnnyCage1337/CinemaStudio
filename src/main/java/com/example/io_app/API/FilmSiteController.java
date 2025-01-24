package com.example.io_app.API;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.API.FindingFilmController;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;
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
    @FXML
    private TableView<Film> filmTableView;

    @FXML
    private TableColumn<Film, String> titleColumn;

    @FXML
    private TableColumn<Film, String> genreColumn;

    @FXML
    private TableColumn<Film, Integer> durationColumn;

    private FilmRepository filmRepository;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        filmRepository = new FilmRepository();

        // Ustawienie cellValueFactory dla kolumn
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Załaduj dane do TableView
        loadFilmData();
    }

    private void loadFilmData() {
        List<Film> films = filmRepository.findAll();
        ObservableList<Film> observableFilms = FXCollections.observableArrayList(films);
        filmTableView.setItems(observableFilms);
    }

    @FXML
    public void swtichToFilmShowings(ActionEvent event) {
        try {
            // Wczytanie pliku FXML poprzedniego widoku
            FXMLLoader loader = new FXMLLoader(
                    Application.class.getResource("/com/example/io_app/SessionSite.fxml")
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
    public void openCreatingFilmWindow() {
        try {
            // Wczytaj FXML dla formularza
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/io_app/CreatingFilm.fxml"));
            Parent root = loader.load();

            // Pobierz kontroler formularza i ustaw callback, jeśli potrzeba
            CreatingFilmController creatingFilmController = loader.getController();
            creatingFilmController.setOnClose(() -> {
                loadFilmData();
                /*System.out.println("Zamknięto okno i można odświeżyć widok");*/
            });

            // Stwórz nowe okno (Stage)
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
    public void deleteSelectedFilm(){

        Film selectedFilm = filmTableView.getSelectionModel().getSelectedItem();

        // Obsługa braku zaznaczenia filmu
        if (selectedFilm == null) {
            Alert noSelectionAlert = new Alert(Alert.AlertType.INFORMATION);
            noSelectionAlert.setTitle("Brak zaznaczenia");
            noSelectionAlert.setHeaderText("Nie wybrano żadnego filmu do usunięcia");
            noSelectionAlert.setContentText("Zaznacz film na liście, zanim klikniesz 'Usuń'.");
            noSelectionAlert.showAndWait();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Potwierdź usunięcie");
        confirmationAlert.setHeaderText("Czy na pewno chcesz usunąć wybrany film?");
        confirmationAlert.setContentText("Film: " + selectedFilm.getTitle());

        // Czekanie na odpowiedź użytkownika
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Potwierdzenie usunięcia przez uzytkownika
            boolean success = filmRepository.deleteByTitle(selectedFilm.getTitle());
            if (success) {
                // Jeśli usunięcie się powiodło, odświeżamy dane w tabeli
                loadFilmData();
            } else {
                System.out.println("Nie udało się usunąć filmu: " + selectedFilm.getTitle());
            }
        } else {
            // Użytkownik anulował usunięcie
            System.out.println("Usunięcie filmu anulowane.");
        }
    }

    @FXML
    public void handleExitButton(ActionEvent event) {
        // Zamyka aplikację
        Platform.exit();
    }

    @FXML
    public void openFindingFilmWindow() {
        try {
            // Wczytaj FXML dla formularza
            FXMLLoader loader = new FXMLLoader(Application.class.getResource("/com/example/io_app/FindingFilm.fxml"));
            Parent root = loader.load();

            // Pobranie kontrolera formularza i ew. ustawienie callback, jeśli potrzeba
            FindingFilmController findingFilmController = loader.getController();
            /*findingFilmController.setOnClose(() -> {
                loadFilmData();
            });*/

            //przekazanie aktualnego kontrolera (strony głównej filmów) od dziecka - kontroler "Znajdź film"
            findingFilmController.setFilmSiteController(this);

            // Stwórz nowe okno (Stage)
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

    public void searchAndDisplayFilm(String filmTitleFragment) {

        List<Film> foundFilms = filmRepository.findByTitle(filmTitleFragment);

        if (foundFilms != null) {
            // utworzenie listy z obiektami, jeśli znaleziono pasujące
            ObservableList<Film> foundFilmList = FXCollections.observableArrayList(foundFilms);
            filmTableView.setItems(foundFilmList);
        } else {
            // Nic nie znaleziono - wyświetlić alert lub wyczyścić tabelę
            filmTableView.setItems(FXCollections.observableArrayList());
            // Lub:
            // showAlert("Brak wyników", "Nie znaleziono filmu o tytule: " + filmTitle);
        }
    }



}