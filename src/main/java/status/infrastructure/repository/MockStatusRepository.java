package status.infrastructure.repository;

import status.domain.model.Status;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class MockStatusRepository implements StatusRepository {
    private final Map<String, Status> statusMap = new HashMap<>();

    @Override
    public Status findByFeedbackID(String feedbackID) {
        return statusMap.getOrDefault(feedbackID, Status.RECEIVED);
    }

    @Override
    public void saveStatus(String feedbackID, Status status) {
        statusMap.put(feedbackID, status);
    }
}