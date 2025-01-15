package status.domain.events;

import status.domain.model.Status;

public abstract class StatusEvent {
    private final String feedbackID;
    private final Status status;

    public StatusEvent(String feedbackID, Status status) {
        this.feedbackID = feedbackID;
        this.status = status;
    }

    public String getFeedbackID() {
        return feedbackID;
    }

    public Status getStatus() {
        return status;
    }

    public abstract void processEvent();
}
