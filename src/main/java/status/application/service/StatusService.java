package status.application.service;

import feedback.application.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import status.domain.model.Status;
import status.infrastructure.repository.StatusRepository;

@Service
public class StatusService {

//    private Status status;
//    private final String userStatus;


    private final StatusRepository statusRepository;


    // Konstruktor
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void setInitialStatus(String feedbackID){
        Status initialStatus = Status.RECEIVED; //Standard Status
        statusRepository.saveStatus(feedbackID, initialStatus);
    }
//    public StatusService(String userStatus) {
//        this.userStatus = userStatus;
//        this.status = Status.RECEIVED; // Standard-Status
//    }

    // Methode zum Ändern des Status
    public void setStatus(String feedbackID, Status newStatus) {
       statusRepository.saveStatus(feedbackID, newStatus);
        System.out.println("Der Status wurde auf '" + newStatus.getDescription() + "' geändert.");
    }

    // Methode, um den aktuellen Status abzurufen
    public Status getStatus(String feedbackID) {
        return statusRepository.findByFeedbackID(feedbackID);
    }

    // Methode zum "Verschicken" des Feedback-Status (simuliert)
    public void sendStatusUpdate(String feedbackID) {
        Status status = getStatus(feedbackID);
        System.out.println("Der Feedback-Status für ID '" + feedbackID + "' wurde verschickt: " + status.getDescription());

    }

}

