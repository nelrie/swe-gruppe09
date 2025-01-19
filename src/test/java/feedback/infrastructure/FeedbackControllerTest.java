package feedback.infrastructure;

import feedback.application.commands.CreateFeedbackCommand;
import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindeFeedback_NotFound() {
        String id = "1";
        when(feedbackService.findeFeedback(id)).thenReturn(null);

        ResponseEntity<Feedback> response = feedbackController.findeFeedback(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testLoescheFeedback() {
        String id = "1";
        Feedback feedback = new Feedback();
        when(feedbackService.findeFeedback(id)).thenReturn(feedback);

        ResponseEntity<Void> response = feedbackController.loescheFeedback(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(feedbackService, times(1)).loescheFeedback(id);
    }

    @Test
    void testLoescheFeedback_NotFound() {
        String id = "1";
        when(feedbackService.findeFeedback(id)).thenReturn(null);

        ResponseEntity<Void> response = feedbackController.loescheFeedback(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testFindeAlleFeedbacks() {
        List<Feedback> feedbacks = Arrays.asList(new Feedback(), new Feedback());
        when(feedbackService.findeAlleFeedbacks()).thenReturn(feedbacks);

        ResponseEntity<List<Feedback>> response = feedbackController.findeAlleFeedbacks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(feedbacks, response.getBody());
    }
}