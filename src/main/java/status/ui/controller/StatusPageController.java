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
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;
import status.application.service.StatusService;
import status.domain.model.Status;

@Controller
public class StatusPageController {



    @FXML
    private Label statusLabel;

    @FXML
    private TextField feedbackIDField;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ApplicationContext context;



   @FXML
    public void checkFeedbackStatus() {
        String feedbackID = feedbackIDField.getText();
        try {
            String status = feedbackService.getFeedbackStatus(feedbackID);
            statusLabel.setText("Der aktuelle Status Ihres Feedbacks ist: " + status);
            System.out.println("Der aktuelle Status Ihres Feedbacks ist: " + status);
        } catch (IllegalArgumentException e) {
            showAlert(e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle("Feedback Status");
        alert.setContentText(message);
        alert.showAndWait();
    }

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

    // TODO Methode ergänzen zB getStatusFromApi(String feedbackID); Aktuell funktinioniert der Abruf und die Ausgabe des Status nicht, nachdem der Nutzer in das Feld die feedbackID eingegeben hat
    @FXML
    private void getStatusFromApi(String feedbackID) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = "https://api.example.com/feedback/status/" + feedbackID;
            Status status = restTemplate.getForObject(apiUrl, Status.class);
            statusLabel.setText("Der aktuelle Status Ihres Feedbacks ist: " + status.getStatus());
        } catch (Exception e) {
            showAlert("Fehler beim Abrufen des Status von der API.", Alert.AlertType.ERROR);
        }
    }
}


