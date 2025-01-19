package feedback.ui.controller;

import feedback.application.commands.CreateFeedbackCommand;
import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import feedback.exceptions.validation.InputValidator;
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

import static org.springframework.util.ObjectUtils.isEmpty;


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

    @Autowired
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

    public void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        messageArea.clear();
    }


    // Submit-Button-Handler für das Feedback Formular: Methode wird aufgerufen, wenn der Benutzer das Feedback-Formular absendet.
    // Sie extrahiert die Werte aus den Textfeldern und sendet das Feedback asynchron
    @FXML
    public void handleSubmitFeedback() {
    if (areInputsValid()){
        try {
            CreateFeedbackCommand command = new CreateFeedbackCommand(
                    new FullName(firstNameField.getText(), lastNameField.getText()),
                    new Email(emailField.getText()),
                    new Message(messageArea.getText())
            );

            Feedback feedback = feedbackService.erstelleFeedback(command);
            String feedbackID = feedback.getFeedbackID();
            logger.info("Feedback erfolgreich erstellt mit ID: {}", feedbackID);

            // Feedback für den Benutzer
            showAlert("Feedback erfolgreich gesendet! Ihre Feedback-ID ist " + feedbackID + ". Bitte notieren Sie diese, um bei Bedarf den Status Ihres Feedbacks abzurufen.", Alert.AlertType.INFORMATION);

            //Felder zurücksetzen
            clearFields();
        } catch (Exception e) {
            logger.error("Fehler beim Senden des Feedbacks {}", e.getMessage(), e);
            showAlert("Beim Senden des Feedbacks ist ein Fehler aufgetreten " + e.getMessage(), Alert.AlertType.ERROR);        }
    } else {
        logger.warn("Fehler beim Einreichen des Feedbacks: Ungültige Eingabe");
        showAlert("Die Felder dürfen nicht leer sein oder ungültige Eingaben enthalten.", Alert.AlertType.WARNING);
    }

    }

    // Alert Dialog wird angezeigt, um dem Benutzer Informationen/ Fehler zu kommunizieren
    @FXML
    public void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        logger.debug("Alert wird angezeigt:[{}]", message);
    }

    private boolean areInputsValid(){
        return InputValidator.isValidName(firstNameField.getText())
                && InputValidator.isValidName(lastNameField.getText())
                && InputValidator.isValidEmail(emailField.getText())
                && InputValidator.isValidMessage(messageArea.getText());
    }

    // Setter-Methoden für Tests
    public void setFirstNameField(TextField firstNameField) {
        this.firstNameField = firstNameField;
    }

    public void setLastNameField(TextField lastNameField) {
        this.lastNameField = lastNameField;
    }

    public void setEmailField(TextField emailField) {
        this.emailField = emailField;
    }

    public void setMessageArea(TextArea messageArea) {
        this.messageArea = messageArea;
    }


}
