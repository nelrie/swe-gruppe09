package feedback.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import feedback.domain.Feedback;
import feedback.repository.FeedbackRepository;
import feedback.repository.MockFeedbackRepository;
import org.junit.jupiter.api.Test;

public class FeedbackServiceTest {


    @Test
    public void testErstelleFeedback() {

        //Mock-FeedbackRepository erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        //FeedbackService initialisieren mit FeedbackRepository
        FeedbackService service = new FeedbackService(feedbackRepository);

        //Feedback-Objekt erstellen
        Feedback validFeedback = new Feedback("1", "Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");

        Feedback invalidFeedback = new Feedback("", "John123", "DOE!", "john.doe@com ", " ");

    }

    // TODO loescheFeedback
    @Test
    public void testLoescheFeedback() {
        //Mock-FeedbackRepository erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        //FeedbackService initialisieren mit FeedbackRepository
        FeedbackService service = new FeedbackService(feedbackRepository);

        //Feedback-Objekt erstellen
        Feedback validFeedback = new Feedback("1", "Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");

    }
    // TODO findeFeedback
    @Test
    public void testFindeFeedback() {
//Mock-FeedbackRepository erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        //FeedbackService initialisieren mit FeedbackRepository
        FeedbackService service = new FeedbackService(feedbackRepository);

        //Feedback-Objekt erstellen
        Feedback validFeedback = new Feedback("1", "Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");

    }

}