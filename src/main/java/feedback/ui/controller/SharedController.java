package feedback.ui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Objects;

@Controller
public class SharedController {

    // Logger um Fehler zu protokollieren anstelle von e.printStackTrace()
    private static final Logger logger = LoggerFactory.getLogger(SharedController.class);

    @FXML
    private Button startButton;

    @FXML
    private Button feedbackButton;

    @FXML
    private Button statusButton;


    @Autowired
    private ApplicationContext context;

    @FXML
    private VBox root;

    // CSS Einbindung
    @FXML
    public void initialize() {
        // CSS-Datei hinzufügen, falls noch nicht hinzugefügt
        if (!root.getStylesheets().contains("/css/styles.css")) {
            root.getStylesheets().add(Objects.requireNonNull(getClass()
                    .getResource("/css/styles.css")).toExternalForm());
        }

    }

    @FXML
    public void openStartPage(ActionEvent event) {
        loadPage(event, "/fxml/start-page.fxml", "Startseite");
    }


    // Methode zum Öffnen des Feedback Formulars
    @FXML
    private void openFeedbackForm(ActionEvent event) {
        loadPage(event, "/fxml/feedback-form.fxml", "Feedback Formular");
    }


// Methode zum Laden der Seite
    @FXML
    private void loadPage(ActionEvent event, String fxmlFile, String title) {
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
            logger.error("Fehler beim Laden der Seite: " + fxmlFile, e);
        } catch (IllegalStateException e) {
            logger.error("Ungültiges Event-Objekt: " + e.getMessage(), e);

        }

    }

    // Methode zum Öffnen der Status Seite
    @FXML
    private void openStatusPage(ActionEvent event) {
        loadPage(event, "/fxml/status-page.fxml", "Statusseite");

    }
}
