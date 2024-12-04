package feedback.infrastructure;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/startpage")
public class StartController {

    @Autowired
    private ApplicationContext context;

    @GetMapping("/feedback")
    public String openFeedbackForm() {
        // Logik zum Öffnen des Feedback-Formulars
        return "feedback-form";
    }

    @GetMapping("/status")
    public String openStatusPage() {
        // Logik zum Öffnen der Statusseite
        return "status-page";
    }
}
