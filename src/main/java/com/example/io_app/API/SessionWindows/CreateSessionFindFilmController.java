package com.example.io_app.API.SessionWindows;
import com.example.io_app.API.Application;
import com.example.io_app.API.FilmWindows.CreateFilmController;
import com.example.io_app.API.FilmWindows.FindFilmController;
import com.example.io_app.API.FilmWindows.UpdateFilmController;
import com.example.io_app.API.MainSites.FilmSiteController;
import com.example.io_app.API.MainSites.SessionSiteController;
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

public class CreateSessionFindFilmController  implements Initializable {

    private FilmService filmService;

    @FXML    private TableView<FilmDTO> filmTableView;
    @FXML    private TableColumn<FilmDTO, Integer> idFilmColumn;
    @FXML    private TableColumn<FilmDTO, String> titleColumn;
    @FXML    private TableColumn<FilmDTO, String> genreColumn;
    @FXML    private TableColumn<FilmDTO, Integer> durationColumn;

    FilmSiteController filmSiteController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        filmService = new FilmService();

        idFilmColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("duration"));

        // Załaduj dane do TableView
        loadFilmData();
    }

    private void loadFilmData() {

        List<FilmDTO> films = filmService.getAllFilms();

        ObservableList<FilmDTO> observableFilms = FXCollections.observableArrayList(films);
        filmTableView.setItems(observableFilms);

    }



    @FXML
    public void handleFindButton() {
      filmSiteController.handleFindButton();
    }




}