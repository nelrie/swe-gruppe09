package status.ui.controller;

import feedback.application.service.FeedbackService;
import feedback.ui.controller.SharedController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import status.application.service.StatusService;
import status.domain.model.Status;

@Controller
public class StatusPageController {

    // Logger hinzufügen
    private static final Logger logger = LoggerFactory.getLogger(StatusPageController.class);



    // Prüfen ob der Controller korrekt instanziiert wird
    public StatusPageController() {

        logger.info("StatusPageController wird von Spring erstellt.");
    }


    @FXML
    private Label statusLabel;

    @FXML
    private TextField feedbackIDField;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private StatusService statusService;

    @Autowired
    private SharedController sharedController;


    // Methode damit die Statusseite das feedback zur eingegebenen feedbackID richtig abruft und anzeigt:
    // Sie ruft Status aus dem StatusService ab und zeigt ihn in der Benutzeroberfläche
    @FXML
    private void getStatusFromRepository() {
        String feedbackID =feedbackIDField.getText();
        try {

            logger.info("Abrufen des Status für Feedback-ID: " + feedbackID);
            Status status = statusService.getStatus(feedbackID);
            if (status != null) {
                statusLabel.setText("Der aktuelle Status Ihres Feedbacks ist: " + status.getDescription());
            } else {
                statusLabel.setText("Kein Status für die angegebene Feedback-ID gefunden.");
                logger.warn("Kein Status für Feedback-ID: " + feedbackID);
            }
        } catch (IllegalArgumentException e) {
            logger.error("Ungültige Feedback-ID: " + feedbackID, e);
            showAlert("Ungültige Feedback-ID: " + feedbackID, Alert.AlertType.ERROR);
        }
        catch (Exception e)
        {
            logger.error("Fehler beim Abrufen des Status aus dem Repository", e);
            showAlert("Fehler beim Abrufen des Status aus dem Repository.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void goToStart(ActionEvent event) {
        sharedController.openStartPage(event);
    }

    // Fürs Testen, da Methoden privat sind
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
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Feedback Status");
        alert.setContentText(message);
        alert.showAndWait();
    }

}


