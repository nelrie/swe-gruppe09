package feedback.application.service;

import static org.junit.jupiter.api.Assertions.*;

import feedback.application.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import feedback.domain.model.Feedback;
import feedback.domain.repository.FeedbackRepository;
import feedback.domain.repository.MockFeedbackRepository;
import org.junit.jupiter.api.Test;

public class FeedbackServiceTest {

    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;
    private Feedback feedback;

    // Hinzugefügt nach KI-Review: Initialisiert das FeedbackRepository und FeedbackService vor jedem Test
@BeforeEach
public void setUp(){
    //Mock-FeedbackRepository erstellen
    feedbackRepository  = new MockFeedbackRepository();

    //FeedbackService initialisieren mit FeedbackRepository
    feedbackService = new FeedbackService(feedbackRepository);

    //Erstellt ein Feedback Objekt vor jedem Test
    feedback = feedbackService.erstelleFeedback("Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");
}

    //Test zum Erstellen eines Feedbacks
    @Test
    public void testErstelleFeedback() {

        // Findet das erstellte Feedback im Repository
        Feedback gefundenesFeedback = feedbackRepository.findById(feedback.getFeedbackID());

        //Überprüft, dass Feedback nicht null ist
        assertNotNull(gefundenesFeedback);

        // Überprüft, ob die Eigenschaften des Feedbacks korrekt sind
        assertEquals("Lisa-Marie", gefundenesFeedback.getFirstName());
        assertEquals("Heufer-Umlauf", gefundenesFeedback.getLastName());
        assertEquals("lisa-marie.heufer-umlauf@gmail.com", gefundenesFeedback.getEmail());
        assertEquals("Ich liebe die Stadtverwaltung!", gefundenesFeedback.getMessage());


    }


    @Test
    public void testLoescheFeedback() {

       // Löscht Feedback anhand der ID
        feedbackService.loescheFeedback(feedback.getFeedbackID());

        // Versucht, das gelöschte Feedback in Repository zu finden
        Feedback gefundenesFeedback = feedbackRepository.findById(feedback.getFeedbackID());

        //Überprüft, ob das Feedback null ist, also gelöscht wurde
        assertNull(gefundenesFeedback);

    }

    @Test
    public void testFindeFeedback() {
        Feedback gefundenesFeedback = feedbackService.findeFeedback(feedback.getFeedbackID());
        assertNotNull(gefundenesFeedback);
        assertEquals("Lisa-Marie", gefundenesFeedback.getFirstName());
        assertEquals("Heufer-Umlauf", gefundenesFeedback.getLastName());
        assertEquals("lisa-marie.heufer-umlauf@gmail.com", gefundenesFeedback.getEmail());
        assertEquals("Ich liebe die Stadtverwaltung!", gefundenesFeedback.getMessage());


    }

}