package feedback.application.service;

import status.domain.model.Status;

public class StatusService {

    private Status status;
    private final String userStatus;

    // Konstruktor
    public StatusService(String userStatus) {
        this.userStatus = userStatus;
        this.status = Status.RECEIVED; // Standard-Status
    }

    // Methode zum Ändern des Status
    public void setStatus(Status newStatus) {
        this.status = newStatus;
        System.out.println("Der Status wurde auf '" + newStatus.getDescription() + "' geändert.");
    }

    // Methode, um den aktuellen Status abzurufen
    public Status getStatus() {
        return this.status;
    }

    // Methode zum "Verschicken" des Feedback-Status (simuliert)
    public void sendStatusUpdate() {
        System.out.println("Der Benutzer '" + userStatus + "' hat den Feedback-Status verschickt: " + status.getDescription());

    }
}
