package feedback.application.service;

import feedback.application.commands.CreateFeedbackCommand;
import feedback.domain.model.Feedback;
import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import feedback.infrastructure.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
    class FeedbackServiceIntegrationTest {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Test
    void testErstelleFeedbackIntegration() {
        CreateFeedbackCommand command = new CreateFeedbackCommand(
                new FullName("Lisa-Marie", "Heufer-Umlauf"),
                new Email("lisa-marie.heufer-umlauf@gmail.com"),
                new Message("Dies ist das Feedback von Lisa-Marie.")
        );

        Feedback feedback = feedbackService.erstelleFeedback(command);

        assertNotNull(feedback);
        assertEquals("Lisa-Marie Heufer-Umlauf", feedback.getFullName().toString());
        assertEquals("lisa-marie.heufer-umlauf@gmail.com", feedback.getEmail().toString());
        assertEquals("Dies ist das Feedback von Lisa-Marie.", feedback.getMessage().toString());
    }
}