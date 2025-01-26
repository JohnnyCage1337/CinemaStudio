package com.example.io_app.API.FilmWindows;

import com.example.io_app.API.MainSites.FilmSiteController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindFilmController {

    @FXML    private TextField searchField;

    //kontroler rodzica
    private FilmSiteController filmSiteController;

    public void setFilmSiteController(FilmSiteController filmSiteController) {
        this.filmSiteController = filmSiteController;
    }

    @FXML
    public void findFilm() {
        String filmTitleFragment = searchField.getText();

        // Logika wyszukiwania
        //sprawdzenie, czy pole nie jest puste
        if (filmTitleFragment == null || filmTitleFragment.trim().isEmpty()) {
            // jakiś komunikat
            return;
        }

        filmSiteController.processFindFilm(filmTitleFragment);
        closeWindow();
    }


    @FXML
    public void closeWindow() {
        // zamknięcie obecne okno dialogowe
        ((Stage) searchField.getScene().getWindow()).close();
    }
}
