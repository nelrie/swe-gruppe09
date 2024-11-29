package feedback.domain.repository;

import feedback.domain.model.Feedback;

import java.util.ArrayList;
import java.util.List;

public class MockFeedbackRepository implements FeedbackRepository {
    private final List<Feedback> feedbackList = new ArrayList<>();

    @Override
    public void save(Feedback feedback) {
        feedbackList.add(feedback);
    }

    @Override
    public List<Feedback> findAll() {
        return feedbackList;
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
}
