package feedback.infrastructure;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import feedback.domain.Feedback;
import feedback.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;
import java.util.List;

public class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testErstelleFeedback() {
        // Arrange
        Feedback feedback = new Feedback("1", "John", "Doe", "john.doe@example.com", "Great app!");
        when(feedbackService.erstelleFeedback(anyString(), anyString(), anyString(), anyString())).thenReturn(feedback);

        // Act
        Feedback result = feedbackController.erstelleFeedback(feedback);

        // Assert
        assertNotNull(result);
        assertEquals(feedback, result);
    }

    @Test
    void testFindeFeedback() {
        // Arrange
        String feedbackId = "1";
        Feedback feedback = new Feedback(feedbackId, "Jane", "Doe", "jane.doe@example.com", "Excellent support!");
        when(feedbackService.findeFeedback(feedbackId)).thenReturn(feedback);

        // Act
        Feedback result = feedbackController.findeFeedback(feedbackId);

        // Assert
        assertNotNull(result);
        assertEquals(feedback, result);
    }

    @Test
    void testLoescheFeedback() {
        // Arrange
        String feedbackId = "1";
        doNothing().when(feedbackService).loescheFeedback(feedbackId);

        // Act
        feedbackController.loescheFeedback(feedbackId);

        // Assert
        verify(feedbackService, times(1)).loescheFeedback(feedbackId);
    }

    @Test
    void testFindeAlleFeedbacks() {
        // Arrange
        List<Feedback> feedbackList = Arrays.asList(
                new Feedback("1", "John", "Doe", "john.doe@example.com", "Great app!"),
                new Feedback("2", "Jane", "Doe", "jane.doe@example.com", "Excellent support!")
        );
        when(feedbackService.findeAlleFeedbacks()).thenReturn(feedbackList);

        // Act
        List<Feedback> result = feedbackController.findeAlleFeedbacks();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(feedbackList, result);
    }
}
