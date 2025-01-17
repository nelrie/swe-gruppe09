package status.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import status.domain.model.Status;

public class StatusAbgefragtEvent extends StatusEvent {
    private static final Logger logger = LoggerFactory.getLogger(StatusAbgefragtEvent.class);

    public StatusAbgefragtEvent(String feedbackID, Status currentStatus) {
        super(feedbackID, currentStatus);
    }

    @Override
    public void processEvent() {
        try {
            logger.info("Status für Feedback-ID '{}' wurde abgefragt. Aktueller Status: {}", getFeedbackID(), getStatus().getDescription());
            System.out.println("Status für Feedback-ID '" + getFeedbackID() + "' wurde abgefragt. Aktueller Status: " + getStatus().getDescription());
        } catch (Exception e) {
            logger.error("Fehler bei der Verarbeitung des StatusAbgefragtEvent für Feedback-ID '{}'", getFeedbackID(), e);
        }
    }
}
