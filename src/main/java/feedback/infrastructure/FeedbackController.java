package feedback.infrastructure;

import feedback.application.commands.CreateFeedbackCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // @RequestBody weist Spring Boot an, den Inhalt des HTTP-Requests in ein Feedback Objekt umwandeln soll
    @PostMapping
    public ResponseEntity<Feedback> erstelleFeedback(@RequestBody CreateFeedbackCommand feedbackCommand) {
        try {
            Feedback erstelltesFeedback = feedbackService.erstelleFeedback(feedbackCommand);
            return new ResponseEntity<>(erstelltesFeedback, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//@PathVariable String id: sagt dem Programm, dass es den Wert des Pfadparameters id in die Methode einfügen soll
    // die Pfadvariable ist ein Teil der URL, wird im Controller verwendet,um das spezifische Feedback zu identifizieren, das abgerufen oder gelöscht werden soll
    // die Pfadvariable id in der URL entspricht der feedbackID des Feedback-Objekts.
    @GetMapping("/{id}")
    public ResponseEntity<Feedback> findeFeedback(@PathVariable String id) {
        Feedback feedback = feedbackService.findeFeedback(id);
        if (feedback == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
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
