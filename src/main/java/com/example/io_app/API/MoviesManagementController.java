package com.example.io_app.API;

import com.example.io_app.DOMAIN.Film;
import com.example.io_app.INFRASTRUCTURE.FilmRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MoviesManagementController implements Initializable
{
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
                    HelloApplication.class.getResource("/com/example/io_app/hello-view.fxml")
            );
            Parent root = loader.load();

            // Pobranie bieżącego Stage (okna)
            Stage stage = HelloApplication.getMainStage();

            // Ustawienie nowej sceny w oknie
            stage.setScene(new Scene(root));
            stage.setTitle("StudioCinema");


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openFormWindow() {
        try {
            // Wczytaj FXML dla formularza
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/io_app/Popout.fxml"));
            Parent root = loader.load();

            // Pobierz kontroler formularza i ustaw callback, jeśli potrzeba
            com.example.io_app.API.FormController formController = loader.getController();
            formController.setOnClose(() -> {
                loadFilmData();
                /*System.out.println("Zamknięto okno i można odświeżyć widok");*/
            });

            // Stwórz nowe okno (Stage)
            Stage stage =  new Stage();
            stage.setTitle("Dodaj nowy film");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL); // Okno modalne
            stage.initOwner(HelloApplication.getMainStage());
            stage.showAndWait(); // Poczekaj na zamknięcie okna
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
