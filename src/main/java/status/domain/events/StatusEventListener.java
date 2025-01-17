package status.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StatusEventListener {
    private static final Logger logger = LoggerFactory.getLogger(StatusEventListener.class);




    @EventListener
    public void handleStatusAngepasstEvent(StatusAngepasstEvent event) {
        handleEvent(event);
    }

    @EventListener
    public void handleStatusEmpfangenEvent(StatusEmpfangenEvent event) {
        handleEvent(event);
    }

    @EventListener
    public void handleStatusAbgefragtEvent(StatusAbgefragtEvent event) {
        handleEvent(event);
    }

    // Logger info wird für den jeweiligen Event-Typ (event.getClass().getSimpleName()) zurückgegeben
    private void handleEvent(StatusEvent event){
        logger.info("{} empfangen für Feedback-ID: {}", event.getClass().getSimpleName(), event.getFeedbackID());
        event.processEvent();
    }

}
