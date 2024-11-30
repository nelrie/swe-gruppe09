package feedback.infrastructure.repository;

import feedback.domain.model.Feedback;

import java.util.List;

// Repository ist die Schnittstelle zwischen der Anwendung und der Datenbank
public interface FeedbackRepository {
    List<Feedback> findAll();
    void save(Feedback feedback);
    Feedback findById(String feedbackID);
    void deleteById(String feedbackID);

}
