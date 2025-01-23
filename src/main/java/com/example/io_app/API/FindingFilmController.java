package com.example.io_app.API;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FindingFilmController {

    @FXML
    private TextField searchField;

    @FXML
    public void searchFilm() {
        String filmTitle = searchField.getText();
        System.out.println("Szukany tytuł: " + filmTitle);
        // Logika wyszukiwania
    }
}
