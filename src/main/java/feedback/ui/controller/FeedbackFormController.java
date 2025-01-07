package feedback.ui.controller;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;

import org.springframework.stereotype.Controller;
import java.util.Objects;


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
    private FeedbackService feedbackService;

   @Autowired
   private ApplicationContext context;

   @Autowired
   private SharedController sharedController;

   @FXML
   private VBox root;


//    @Autowired
//    public FeedbackFormController(FeedbackService feedbackService) {
//
//        this.feedbackService = feedbackService;
//    }

    // CSS Einbindung
    @FXML
    public void initialize() {
        // Textfelder leeren
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        messageArea.clear();

        // CSS-Datei hinzufügen, falls noch nicht hinzugefügt
        if (!root.getStylesheets().contains("/css/styles.css")) {
            root.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("/css/styles.css")).toExternalForm());
        }
        if (!root.getStylesheets().contains("/css/feedbackseite.css")) {
            root.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("/css/feedbackseite.css")).toExternalForm());
        }


    }


    // Methode wird aufgerufen, wenn der Benutzer das Feedback-Formular absendet.
    // Sie extrahiert die Werte aus den Texfeldern und sendet das Feedback asynchron
    @FXML
    private void handleSubmitFeedback(ActionEvent event) {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        String message = messageArea.getText();

        // Task damit das Senden vom Feedback asynchron ausgeführt wird
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    Feedback feedback = feedbackService.erstelleFeedback(firstName, lastName, email, message);
                    String feedbackID = feedback.getFeedbackID();

                    // Benutzeroberfläche aktualisieren
                    Platform.runLater(() -> showAlert("Feedback erfolgreich gesendet! Ihre Feedback-ID ist " + feedbackID + ". Bitte notieren Sie diese, um bei Bedarf den Status Ihres Feedbacks abzurufen.", Alert.AlertType.INFORMATION, event));
                } catch (IllegalArgumentException e) {
                    logger.error("Fehler beim Einreichen des Feedbacks: Ungültige Eingabe");
                    Platform.runLater(() -> showAlert(e.getMessage(), Alert.AlertType.ERROR, event));
                } catch (Exception e) {
                    logger.error("Fehler beim Einreichen des Feedback", e);
                    Platform.runLater(() -> showAlert("Fehler beim Senden des Feedbacks.", Alert.AlertType.ERROR, event));
                }
                return null;
            }
        };

        //startet Task in neuem Thread
        new Thread(task).start();
    }


    // Alert Dialog wird angezeigt, um dem Benutzer Infos/Fehler zu kommunizieren
    @FXML
    private void showAlert(String message, Alert.AlertType type, ActionEvent event) {
        Alert alert = new Alert(type);
        alert.setTitle("Feedback");
        alert.setContentText(message);
        alert.showAndWait().ifPresent(response -> goToStart(event));
    }

    // Methode wird aufgerufen, um zur Startseite zurückzukehren.
    // Verwendet den SharedController, um die Startseite zu öffnen.
    @FXML
    private void goToStart(ActionEvent event) {
        sharedController.openStartPage(event);
    }


}
