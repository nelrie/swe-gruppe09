package status.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import status.domain.model.Status;

public class StatusEmpfangenEvent extends StatusEvent {
    private static final Logger logger = LoggerFactory.getLogger(StatusEmpfangenEvent.class);

    public StatusEmpfangenEvent(String feedbackID, Status initialStatus) {
        super(feedbackID, initialStatus);
    }

    @Override
    public void processEvent() {
        try {
            logger.info("Status für Feedback-ID '{}' wurde empfangen: {}", getFeedbackID(), getStatus().getDescription());
            System.out.println("Status für Feedback-ID '" + getFeedbackID() + "' wurde empfangen: " + getStatus().getDescription());
        } catch (Exception e) {
            logger.error("Fehler bei der Verarbeitung des StatusEmpfangenEvent für Feedback-ID '{}'", getFeedbackID(), e);
        }
    }
}