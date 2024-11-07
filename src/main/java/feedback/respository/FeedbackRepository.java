package feedback.respository;

import feedback.domain.Feedback;
import java.util.List;

// Repository ist die Schnittstelle zwischen der Anwendung und der Datenbank
public interface FeedbackRepository {
    void save(Feedback feedback);
    Feedback findById(String feedbackID);
    List<Feedback> findAll();
    Feedback update(Feedback feedback);
    void deleteById(String feedbackID);

}
