package feedback.infrastructure.repository;

import feedback.domain.model.Feedback;
import org.springframework.stereotype.Repository;
import java.util.List;

// Repository ist die Schnittstelle zwischen der Anwendung und der Datenbank
@Repository // Spring-Annotation für die Verwaltung von Datenbankzugriffen ChatGPT
public interface FeedbackRepository {
    List<Feedback> findAll();
    void save(Feedback feedback);
    Feedback findById(String feedbackID);
    void deleteById(String feedbackID);

}
