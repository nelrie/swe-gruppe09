package status.ui.controller;

import feedback.application.service.FeedbackService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import status.application.service.StatusService;
import status.domain.model.Status;

@Controller
public class StatusPageController {

    // Prüfen ob der Controller korrekt instanziiert wird
    public StatusPageController() {
        System.out.println("StatusPageController wird von Spring erstellt.");
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




    @FXML
    private void goToStart() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/start-page.fxml"));
            loader.setControllerFactory(context::getBean);
            Parent root = loader.load();
            Stage stage = (Stage) feedbackIDField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Startseite");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // Methode damit die Statusseite das feedback zur eingegebenen feedbackID richtig abruft und anzeigt:
    // Sie ruft Status aus dem StatusService ab und zeigt ihn in der Benutzeroberfläche
    @FXML
    private void getStatusFromRepository() {
        try {
            String feedbackID =feedbackIDField.getText();
            Status status = statusService.getStatus(feedbackID);
            if (status != null) {
                statusLabel.setText("Der aktuelle Status Ihres Feedbacks ist: " + status.getDescription());
            } else {
                statusLabel.setText("Kein Status für die angegebene Feedback-ID gefunden.");
            }
        }
        catch (Exception e)
        {
            showAlert("Fehler beim Abrufen des Status aus dem Repository.", Alert.AlertType.ERROR);
        }
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

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Feedback Status");
        alert.setContentText(message);
        alert.showAndWait();
    }

}


