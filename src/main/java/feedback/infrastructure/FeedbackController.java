package feedback.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;

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
    public ResponseEntity<Feedback> erstelleFeedback(@RequestBody Feedback feedback) {
         Feedback erstelltesFeedback = feedbackService.erstelleFeedback(
                feedback.getFirstName(),
                feedback.getLastName(),
                feedback.getEmail(),
                feedback.getMessage()
        );
         return new ResponseEntity<>(erstelltesFeedback, HttpStatus.CREATED);
    }
//@PathVariable String id: sagt dem Programm, dass es den Wert des Pfadparameters id in die Methode einfügen soll
    // die Pfadvariable ist ein Teil der URL, wird im Controller verwendet,um das spezifische Feedback zu identifizieren, das abgerufen oder gelöscht werden soll
    // die Pfadvariable id in der URL entspricht der feedbackID des Feedback-Objekts.
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> findeFeedback(@PathVariable String id) {
        Feedback feedback = feedbackService.findeFeedback(id);
        if (feedback == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> loescheFeedback(@PathVariable String id) {
        Feedback feedback = feedbackService.findeFeedback(id);
        if (feedback != null) {
            feedbackService.loescheFeedback(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
    }
    @GetMapping
    public ResponseEntity<List<Feedback>> findeAlleFeedbacks() {
        List<Feedback> feedbacks = feedbackService.findeAlleFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }
}
