package feedback.domain.events;

import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FeedbackAngelegtEvent {
    private final String feedbackID;
    private final FullName fullName;
    private final Email email;
    private final Message message;

    private static final Logger logger = LoggerFactory.getLogger(FeedbackAngelegtEvent.class);

    public FeedbackAngelegtEvent(String feedbackID, FullName fullName, Email email, Message message) {
        this.feedbackID = feedbackID;
        this.fullName = fullName;
        this.email = email;
        this.message = message;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public FullName getFullName() {
        return fullName;
    }

    public Email getEmail() {
        return email;
    }

    public Message getMessage() {
        return message;
    }

    public void processEvent() {
        try {
            // Logik zur Verarbeitung des FeedbackAngelegtEvent
            logger.info("Verarbeite FeedbackAngelegtEvent für Feedback-ID '{}'", feedbackID);
            System.out.println("Feedback mit ID '" + feedbackID + "' wurde angelegt von '" + fullName + "' mit der Nachricht: " + message);
        } catch (Exception e) {
            logger.error("Fehler bei der Verarbeitung des FeedbackAngelegtEvent für Feedback-ID '{}'", feedbackID, e);
        }
    }
}
