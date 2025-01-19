package feedback.infrastructure.repository;

import feedback.domain.model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackRepositoryTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackRepositoryTest feedbackRepositoryTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        List<Feedback> feedbackList = List.of(new Feedback(), new Feedback());
        when(feedbackRepository.findAll()).thenReturn(feedbackList);

        List<Feedback> result = feedbackRepository.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testSave() {
        Feedback feedback = new Feedback();
        doNothing().when(feedbackRepository).save(feedback);

        feedbackRepository.save(feedback);

        verify(feedbackRepository, times(1)).save(feedback);
    }

    @Test
    void testFindById() {
        String feedbackID = "1";
        Feedback feedback = new Feedback();
        when(feedbackRepository.findById(feedbackID)).thenReturn(feedback);

        Feedback result = feedbackRepository.findById(feedbackID);

        assertNotNull(result);
        assertEquals(feedback, result);
    }

    @Test
    void testDeleteById() {
        String feedbackID = "1";
        doNothing().when(feedbackRepository).deleteById(feedbackID);

        feedbackRepository.deleteById(feedbackID);

        verify(feedbackRepository, times(1)).deleteById(feedbackID);
    }
}