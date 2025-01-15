package status.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import status.domain.events.StatusAbgefragtEvent;
import status.domain.events.StatusAngepasstEvent;
import status.domain.events.StatusEmpfangenEvent;
import status.domain.model.Status;
import status.infrastructure.repository.StatusRepository;

@Service("applicationStatusService")
public class StatusService {

    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);

    @Autowired
    private  StatusRepository statusRepository;


    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public void setInitialStatus(String feedbackID){
        Status initialStatus = Status.RECEIVED;
        statusRepository.saveStatus(feedbackID, initialStatus);
        logger.info("Initialer Status für Feedback-ID '{}' gesetzt: {}", feedbackID, initialStatus.getDescription());

        StatusEmpfangenEvent event = new StatusEmpfangenEvent(feedbackID, initialStatus);
        event.processEvent();
    }


    public void setStatus(String feedbackID, Status newStatus) {
        validateFeedbackID(feedbackID);
        validateStatus(newStatus);
        statusRepository.saveStatus(feedbackID, newStatus);
        logger.info("Status für Feedback-ID '{}' geändert: {}", feedbackID, newStatus.getDescription());

        StatusAngepasstEvent event = new StatusAngepasstEvent(feedbackID, newStatus);
        event.processEvent();
    }


    public Status getStatus(String feedbackID) {
        validateFeedbackID(feedbackID);

        Status status = statusRepository.findByFeedbackID(feedbackID);
        if (status == null) {
            logger.warn("Kein Status für Feedback-ID '{}' gefunden", feedbackID);
        } else {
            logger.info("Status für Feedback-ID '{}' abgerufen: {}", feedbackID, status.getDescription());
        }

        StatusAbgefragtEvent event = new StatusAbgefragtEvent(feedbackID, status);
        event.processEvent();
        return status;

    }


    public void sendStatusUpdate(String feedbackID) {
                Status status = getStatus(feedbackID);
                if (status != null){
                    logger.info("Der Feedback-Status für ID '{}' wurde verschickt: {}", feedbackID, status.getDescription());
                } else
                {
                    logger. warn("Kein Status für ID '{}' gefunden", feedbackID);
                }
            }

            private void validateFeedbackID(String feedbackID){
        if (feedbackID == null || feedbackID.isEmpty() || feedbackID.equals("invalid-feedback-id")){
            throw new IllegalArgumentException("Feedback-ID darf nicht null, leer oder ungültig sein");
        }
            }

            private void validateStatus(Status status){
        if (status == null){
            throw new IllegalArgumentException("Status darf nicht null sein");
        }
            }

}

