package feedback.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class StartPageController {

    @Autowired
    private ApplicationContext context;


    @FXML
    private void openFeedbackForm(ActionEvent event) {
    try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/feedback-form.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Parent root = fxmlLoader.load();
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Feedback Formular");
        stage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    private void openStatusPage(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/status-page.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Statusseite");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}