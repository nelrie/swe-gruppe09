package status.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import status.domain.model.Status;

public class StatusAngepasstEvent extends StatusEvent {

private static final Logger logger= LoggerFactory.getLogger(StatusAngepasstEvent.class);
    public StatusAngepasstEvent(String feedbackID, Status newStatus) {
        super(feedbackID, newStatus);
    }


    @Override
    public void processEvent() {
        try{
        logger.info("Verarbeite StatusAngepasstEvent für Feedback-ID '{}'", getFeedbackID());
        System.out.println("Status für Feedback-ID '" + getFeedbackID() + "' wurde zu '" + getStatus().getDescription() + "' geändert.");
    } catch (Exception e) {
        logger.error("Fehler bei der Verarbeitung des StatusAngepasstEvent für Feedback-ID '{}'", getFeedbackID(), e);
        }
    }
}


