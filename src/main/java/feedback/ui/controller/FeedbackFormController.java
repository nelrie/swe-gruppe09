package feedback.ui.controller;

import feedback.application.commands.CreateFeedbackCommand;
import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;

import org.springframework.stereotype.Controller;


//enthält die Logik für das JavaFX-Feedback-Formular
@Controller
public class FeedbackFormController {
    private static final Logger logger = LoggerFactory.getLogger(FeedbackFormController.class);

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextArea messageArea;

    private final FeedbackService feedbackService;



@Autowired
public FeedbackFormController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;

}


    // CSS Einbindung
    @FXML
    public void initialize() {
        clearFields();

    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        messageArea.clear();
    }


    // Methode wird aufgerufen, wenn der Benutzer das Feedback-Formular absendet.
    // Sie extrahiert die Werte aus den Textfeldern und sendet das Feedback asynchron
    @FXML
    private void handleSubmitFeedback() {
        // Abrufen der Benutzereingaben
        CreateFeedbackCommand command = getFeedbackCommand();
        submitFeedbackAsync(command);


    }

    private void submitFeedbackAsync(CreateFeedbackCommand command) {
        // Task damit das Senden vom Feedback asynchron ausgeführt wird (LLM Empfehlung, damit UI reaktionsfähig ist)
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    Feedback feedback = feedbackService.erstelleFeedback(command);
                    String feedbackID = feedback.getFeedbackID();

                    logger.info("Feedback erfolgreich erstellt mit ID: {}", feedbackID);

                    // Benutzeroberfläche aktualisieren
                    Platform.runLater(() -> {
                        showAlert("Feedback erfolgreich gesendet! Ihre Feedback-ID ist " + feedbackID + ". Bitte notieren Sie diese, um bei Bedarf den Status Ihres Feedbacks abzurufen.", Alert.AlertType.INFORMATION);
                    clearFields();
                });
                } catch (IllegalArgumentException e) {
                    logger.error("Fehler beim Einreichen des Feedbacks: Ungültige Eingabe");
                    Platform.runLater(() -> showAlert(e.getMessage(), Alert.AlertType.ERROR));
                } catch (Exception e) {
                    logger.error("Fehler beim Einreichen des Feedback", e);
                    Platform.runLater(() -> showAlert("Fehler beim Senden des Feedbacks.", Alert.AlertType.ERROR));
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private CreateFeedbackCommand getFeedbackCommand() {
        String firstNameInput = firstNameField.getText();
        String lastNameInput = lastNameField.getText();
        String emailInput = emailField.getText();
        String messageInput = messageArea.getText();

        // Erstellen der Value Objects
        FullName fullName = new FullName(firstNameInput, lastNameInput);
        Email email = new Email(emailInput);
        Message message = new Message(messageInput);

        // Erstellen des Commands
        return new CreateFeedbackCommand(fullName, email, message);
    }


    // Alert Dialog wird angezeigt, um dem Benutzer Informationen/ Fehler zu kommunizieren
    @FXML
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Feedback");
        alert.setContentText(message);
        alert.showAndWait();
    }


}
