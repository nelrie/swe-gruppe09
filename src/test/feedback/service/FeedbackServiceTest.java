package feedback.service;

import feedback.model.Feedback;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FeedbackServiceTest {

    @Test
    public void testSubmitFeedback() {
        FeedbackService service = new FeedbackService();

        Feedback validFeedback = new Feedback();
        validFeedback.setFirstName("John Doe");
        validFeedback.setFirstName("Lisa-Marie");
        validFeedback.setLastName("Doe");
        validFeedback.setLastName("Heufer-Umlauf");
        validFeedback.setEmail("john.doe@example.com");
        validFeedback.setMessage("I love the city administration!");

        assertDoesNotThrow(() -> service.submitFeedback(validFeedback));

        Feedback invalidFeedback = new Feedback();
        invalidFeedback.setFirstName("John123");
        invalidFeedback.setLastName("Doe!");
        invalidFeedback.setEmail("john.doe@com");

        assertThrows(IllegalArgumentException.class, () -> service.submitFeedback(invalidFeedback));
    }
}