package feedback.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.Objects;

@Controller
public class StartPageController {

    @Autowired
    private ApplicationContext context;


    @FXML
    private TextFlow contentText;


    // CSS Einbindung
    @FXML
    public void initialize() {

//        // CSS-Datei hinzufügen
//        if (!root.getStylesheets().contains("/css/styles.css")) {
//            root.getStylesheets().add(Objects.requireNonNull(getClass()
//                    .getResource("/css/styles.css")).toExternalForm());
//        }

        contentText.setLineSpacing(10);
        }

}