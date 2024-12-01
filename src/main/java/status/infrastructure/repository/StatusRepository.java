package status.infrastructure.repository;

import org.springframework.stereotype.Repository;
import status.domain.model.Status;

@Repository
public interface StatusRepository {
    Status findByFeedbackID(String feedbackID);
    void saveStatus(String feedbackID, Status status);
}