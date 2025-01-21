package feedback.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.text.TextFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class StartPageController {

    @Autowired
    private ApplicationContext context;

    @FXML
    private TextFlow contentText;

    // CSS Einbindung
    @FXML
    public void initialize() {

        contentText.setLineSpacing(10);
        }
}