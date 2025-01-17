package feedback.domain.events;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class FeedbackEventListener {
    private static final Logger logger =
            LoggerFactory.getLogger(FeedbackEventListener.class);

    @EventListener
    public void handleFeedbackAngelegtEvent(FeedbackAngelegtEvent event) {
        logger.info("FeedbackAngelegtEvent empfangen: {}", event);

    }
}
