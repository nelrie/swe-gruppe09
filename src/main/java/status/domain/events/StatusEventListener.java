package status.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class StatusEventListener {
    private static final Logger logger =
            LoggerFactory.getLogger(status.domain.events.StatusEventListener.class);

    @EventListener
    public void handleStatusAbgerufen(StatusAngepasstEvent event) {
        logger.info("StatusAbgerufenEvent empfangen: {}", event);

    }
}
