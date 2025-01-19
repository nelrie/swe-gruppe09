package feedback.infrastructure.repository;

import feedback.domain.model.Feedback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MockFeedbackRepositoryTest {

    private MockFeedbackRepository mockFeedbackRepository;

    @BeforeEach
    public void setUp() {
        mockFeedbackRepository = new MockFeedbackRepository();
    }

    @Test
    void testSave() {
        Feedback feedback = new Feedback();
        feedback.setFeedbackID("1");
        mockFeedbackRepository.save(feedback);

        List<Feedback> feedbackList = mockFeedbackRepository.findAll();
        assertEquals(1, feedbackList.size());
        assertTrue(feedbackList.contains(feedback));
    }

    @Test
    void testFindAll() {
        Feedback feedback1 = new Feedback();
        feedback1.setFeedbackID("1");
        Feedback feedback2 = new Feedback();
        feedback2.setFeedbackID("2");

        mockFeedbackRepository.save(feedback1);
        mockFeedbackRepository.save(feedback2);

        List<Feedback> feedbackList = mockFeedbackRepository.findAll();
        assertEquals(2, feedbackList.size());
        assertTrue(feedbackList.contains(feedback1));
        assertTrue(feedbackList.contains(feedback2));
    }

    @Test
    void testFindById() {
        Feedback feedback = new Feedback();
        feedback.setFeedbackID("1");
        mockFeedbackRepository.save(feedback);

        Feedback foundFeedback = mockFeedbackRepository.findById("1");
        assertNotNull(foundFeedback);
        assertEquals("1", foundFeedback.getFeedbackID());

        Feedback notFoundFeedback = mockFeedbackRepository.findById("2");
        assertNull(notFoundFeedback);
    }

    @Test
    void testDeleteById() {
        Feedback feedback = new Feedback();
        feedback.setFeedbackID("1");
        mockFeedbackRepository.save(feedback);

        mockFeedbackRepository.deleteById("1");
        Feedback deletedFeedback = mockFeedbackRepository.findById("1");
        assertNull(deletedFeedback);
    }
}