package feedback.service;

import feedback.domain.Feedback;
import feedback.respository.FeedbackRepository;
import feedback.respository.MockFeedbackRepository;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {

    @Test
    public void testErstelleFeedback() {

        //Mock-FeedbackRepository erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        //FeedbackService initialisieren mit FeedbackRepository
        FeedbackService service = new FeedbackService(feedbackRepository);

        //Feedback-Objekt erstellen
        Feedback validFeedback = new Feedback("1", "Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");

        Feedback invalidFeedback = new Feedback("-", "John123", "DOE!", "john.doe@com ", " ");

    }
}