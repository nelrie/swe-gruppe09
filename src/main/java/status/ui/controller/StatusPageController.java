package status.ui.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import status.application.service.StatusService;
import status.domain.model.Status;

@Controller
public class StatusPageController {

    private static final Logger logger = LoggerFactory.getLogger(StatusPageController.class);

    @FXML
    public Button submitButton;

    @FXML
    private Label statusLabel;

    @FXML
    private TextField feedbackIDField;

    private final StatusService statusService;

    @Autowired
    public StatusPageController(StatusService statusService) {
        this.statusService = statusService;

        logger.info("StatusPageController wird von Spring erstellt.");
    }

    // Initialisierungsmethode
    @FXML
    public void initialize() {
        logger.info("StatusPageController wurde initialisiert.");
        // Standardtext für das Label setzen
        statusLabel.setText("Bitte geben Sie Ihre Feedback-ID ein.");
    }

    // Methode wird aufgerufen, wenn der Benutzer den Submit-Button klickt
    @FXML
    private void handleSubmitButtonAction() {
        getStatusFromRepository();
    }


    @FXML
    private void getStatusFromRepository() {
        String feedbackID =feedbackIDField.getText();
        try {

            logger.info("Abrufen des Status für Feedback-ID: {}", feedbackID);
            Status status = statusService.getStatus(feedbackID);
            if (status != null) {
                statusLabel.setText("Der aktuelle Status Ihres Feedbacks ist: " + status.getDescription());
            } else {
                statusLabel.setText("Kein Status für die angegebene Feedback-ID gefunden.");
                logger.warn("Kein Status für Feedback-ID: {}", feedbackID);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Ungültige Feedback-ID: {}", feedbackID, e);
            showAlert("Ungültige Feedback-ID: " + feedbackID);
        }
        catch (Exception e)
        {
            logger.error("Fehler beim Abrufen des Status aus dem Repository", e);
            showAlert("Fehler beim Abrufen des Status aus dem Repository.");
        }
    }

    public void testableGetStatusFromRepository() {
        getStatusFromRepository();
    }

    public TextField getFeedbackIDField() {
        return feedbackIDField;
    }
    public Label getStatusLabel() {
        return statusLabel;
    }

    @FXML
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Feedback Status");
        alert.setContentText(message);
        alert.showAndWait();
    }

}


