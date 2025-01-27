package com.example.io_app.API.SessionWindows;

import com.example.io_app.API.MainSites.SessionSiteController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FindSessionByFilmTitleController {

    @FXML    private TextField searchField;

    //kontroler rodzica
    private SessionSiteController sessionSiteController;

    public void setSessionSiteController(SessionSiteController sessionSiteController) {
        this.sessionSiteController = sessionSiteController;
    }

    @FXML
    public void findSessionByTitle() {
        String filmTitleFragment = searchField.getText();

        // Logika wyszukiwania
        //sprawdzenie, czy pole nie jest puste
        if (filmTitleFragment == null || filmTitleFragment.trim().isEmpty()) {
            // jakiś komunikat
            return;
        }

        sessionSiteController.processFindSessionByFilmTitle(filmTitleFragment);
        closeWindow();
    }

    @FXML
    public void closeWindow() {
        // zamknięcie obecne okno dialogowe
        ((Stage) searchField.getScene().getWindow()).close();
    }
}
