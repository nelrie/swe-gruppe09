package feedback.infrastructure;

import feedback.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import feedback.repository.FeedbackRepository;
import feedback.repository.MockFeedbackRepository;
import feedback.service.FeedbackService;
import feedback.domain.Feedback;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    // Konstruktor um den FeedbackService zu injizieren
    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // @RequestBody weist Spring Boot an, den Inhalt des HTTP-Requests in ein Feedback Objekt umwandeln soll
    @PostMapping
    public Feedback erstelleFeedback(@RequestBody Feedback feedback) {
        return feedbackService.erstelleFeedback(
                feedback.getFirstName(),
                feedback.getLastName(),
                feedback.getEmail(),
                feedback.getMessage()
        );
    }
//@PathVariable String id: sagt dem Programm, dass es den Wert des Pfadparameters id in die Methode einfügen soll
    // die Pfadvariable ist ein Teil der URL, wird im Controller verwendet,um das spezifische Feedback zu identifizieren, das abgerufen oder gelöscht werden soll
    // die Pfadvariable id in der URL entspricht der feedbackID des Feedback-Objekts.
    @GetMapping("/{id}")
    public Feedback findeFeedback(@PathVariable String id) {
        return feedbackService.findeFeedback(id);
    }

    @DeleteMapping("/{id}")
    public void loescheFeedback(@PathVariable String id) {
        feedbackService.loescheFeedback(id);
    }
    @GetMapping
    public List<Feedback> findeAlleFeedbacks() {
        return feedbackService.findeAlleFeedbacks();
    }
}
