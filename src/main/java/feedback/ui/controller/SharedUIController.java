package feedback.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class SharedUIController {

    // Logger um Fehler zu protokollieren anstelle von e.printStackTrace()
    private static Logger logger = LoggerFactory.getLogger(SharedUIController.class);

    @FXML
    private ToggleButton startButton;

    @FXML
    private ToggleButton feedbackButton;

    @FXML
    private ToggleButton statusButton;


    @Autowired
    private ApplicationContext context;


    @FXML
    public void openStartPage(ActionEvent event) {
        setSelectedButton(startButton);
        loadPage(event, "/fxml/start-page.fxml", "Startseite");
    }


    // Methode zum Öffnen des Feedback-Formulars
    @FXML
    public void openFeedbackForm(ActionEvent event) {
        setSelectedButton(feedbackButton);
        loadPage(event, "/fxml/feedback-form.fxml", "Feedback Formular");
    }

    // Methode zum Öffnen der Status Seite
    @FXML
    public void openStatusPage(ActionEvent event) {
        setSelectedButton(statusButton);
        loadPage(event, "/fxml/status-page.fxml", "Statusseite");

    }

    // Styling der Button, wenn selektiert
    private void setSelectedButton(ToggleButton selectedButton) {
        System.out.println("Selected button: " + selectedButton.getText());
        startButton.setSelected(false);
        feedbackButton.setSelected(false);
        statusButton.setSelected(false);
        selectedButton.setSelected(true);
    }



    // Methode zum Laden der Seite
    @FXML
    public void loadPage(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            fxmlLoader.setControllerFactory(context::getBean);
            Parent root = fxmlLoader.load();

            // Überprüfen, ob das Event-Objekt eine Quelle vom Typ Node hat
            if (event.getSource() instanceof Node) {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.setTitle(title);
                stage.show();
            } else {
                throw new IllegalArgumentException("Event source is not a Node");
            }
        } catch (IOException e) {
            logger.error("Fehler beim Laden der Seite: {}", fxmlFile, e);
        } catch (IllegalStateException e) {
            logger.error("Ungültiges Event-Objekt: {}", e.getMessage(), e);

        }

    }

    // Setter fürs Testen
    public void setContext(ApplicationContext context) {
        this.context = context;
    }

    public void setLogger(Logger logger) {
        SharedUIController.logger = logger;
    }

    public static Logger getLogger() {
        return logger;
    }

    public void setStartButton(ToggleButton startButton) {
        this.startButton = startButton;
    }

    public void setFeedbackButton(ToggleButton feedbackButton) {
        this.feedbackButton = feedbackButton;
    }

    public void setStatusButton(ToggleButton statusButton) {
        this.statusButton = statusButton;
    }


}
