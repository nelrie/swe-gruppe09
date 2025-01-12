package feedback.repository;

import feedback.domain.Feedback;
import feedback.domain.Status;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockFeedbackRepository implements FeedbackRepository {
    private static final Logger logger = LoggerFactory.getLogger(MockFeedbackRepository.class);
    private final List<Feedback> feedbackList = new ArrayList<>();

    @Override
    public void save(Feedback feedback) {
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback darf nicht null sein");
        }
        logger.info("Feedback mit ID {} und Status {} wurde gespeichert.", feedback.getFeedbackID(), feedback.getStatus());
        feedbackList.add(feedback);
    }



    @Override
    public Feedback findById(String feedbackID) {
        for (Feedback feedback : feedbackList)
            if (feedback.getFeedbackID().equals(feedbackID)) {
                return feedback;
            }
        return null;
    }

    @Override
    public void deleteById(String feedbackID) {
        feedbackList.removeIf(feedback -> feedback.getFeedbackID().equals(feedbackID));
    }

    @Override
    public List<Feedback> findAll() {
        return new ArrayList<>(feedbackList); // Rückgabe einer Kopie der Liste
    }
}

