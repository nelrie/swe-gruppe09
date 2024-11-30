package feedback.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;
import org.springframework.stereotype.Controller;

//enthält die Logik für das JavaFX-Feedback-Formular
@Controller
public class FeedbackFormController {


    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea messageArea;

    private FeedbackService feedbackService;

    // No-args-controller
    public FeedbackFormController() {

    }
    @Autowired
    public FeedbackFormController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }


    @FXML
    private void handleSubmitFeedback() {
        // Extrahiere die Werte aus den Textfeldern
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String message = messageArea.getText();

        try {
            Feedback feedback = feedbackService.erstelleFeedback(firstName, lastName, email, message);
            showAlert("Feedback erfolgreich gesendet!",
                    Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Fehler beim Senden des Feedbacks.", Alert.AlertType.ERROR);
        }
        }
        private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Feedback");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
