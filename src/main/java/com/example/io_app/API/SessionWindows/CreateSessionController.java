package com.example.io_app.API.SessionWindows;

import com.example.io_app.APPLICATION.SessionService;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateSessionController {

    private SessionService sessionService;

    @FXML    private ComboBox<String> filmComboBox;
    @FXML    private DatePicker datePicker;
    @FXML    private TextField hourField;
    @FXML    private TextField roomField;
    @FXML    private TextField seatsField;
    @FXML    private TextField priceField;

    private Runnable onClose; // Callback po zamknięciu okna

    public CreateSessionController() {
        this.sessionService = new SessionService();
    }

    public void createSession(){

    }

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    @FXML
    public void initialize() {
        // Załaduj listę filmów z serwisu
        //filmComboBox.getItems().addAll(sessionService.getAllFilmTitles());
    }

    @FXML
    public void closeWindow() {
        Stage stage = (Stage) filmComboBox.getScene().getWindow();
        stage.close();
    }
}
