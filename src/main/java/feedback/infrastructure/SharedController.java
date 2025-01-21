package feedback.infrastructure;

import feedback.ui.controller.SharedUIController;
import javafx.event.ActionEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController("sharedControllerInfr")
@RequestMapping("/")
public class SharedController {
    private static final Logger logger = LoggerFactory.getLogger(SharedController.class);

    @Autowired
    @Qualifier("sharedUIController")
    private final SharedUIController sharedUIController;

    @Autowired
    public SharedController(SharedUIController sharedUIController) {
        this.sharedUIController = sharedUIController;
    }

    @GetMapping("/feedback")
    public void openFeedbackForm(ActionEvent event) {
        logger.info("Navigiere zur Feedback-Formular Seite");
        sharedUIController.openFeedbackForm(event);
    }

    @GetMapping("/status")
    public void openStatusPage(ActionEvent event) {
        logger.info("Navigiere zur Status Seite");
        sharedUIController.openStatusPage(event);
    }

    @GetMapping("/start")
    public void openStartPage(ActionEvent event) {
        logger.info("Navigiere zur Startseite");
        sharedUIController.openStartPage(event);
    }
}
