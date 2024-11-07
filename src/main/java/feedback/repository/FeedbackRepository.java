package feedback.repository;

import feedback.domain.Feedback;

// Repository ist die Schnittstelle zwischen der Anwendung und der Datenbank
public interface FeedbackRepository {
    void save(Feedback feedback);
    Feedback findById(String feedbackID);
    void deleteById(String feedbackID);

}
