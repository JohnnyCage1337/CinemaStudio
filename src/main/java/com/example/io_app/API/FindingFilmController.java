package com.example.io_app.API;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindingFilmController {

    @FXML
    private TextField searchField;

    //kontroler rodzica
    private FilmSiteController filmSiteController;

    public void setFilmSiteController(FilmSiteController filmSiteController) {
        this.filmSiteController = filmSiteController;
    }

    @FXML
    public void searchFilm() {
        String filmTitle = searchField.getText();

        // Logika wyszukiwania
        //sprawdzenie, czy pole nie jest puste
        if (filmTitle == null || filmTitle.trim().isEmpty()) {
            // ewentualnie Alert albo jakiś komunikat
            return;
        }

        filmSiteController.searchAndDisplayFilm(filmTitle);
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        // zamknięcie obecne okno dialogowe
        ((Stage) searchField.getScene().getWindow()).close();
    }
}
