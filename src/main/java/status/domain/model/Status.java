package status.domain.model;

// Drei verschiedene Statusanzeigen
public enum Status {
    RECEIVED("Feedback erhalten"),
    IN_PROGRESS("Feedback wird bearbeitet"),
    COMPLETED("Feedback abgeschlossen");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // Getter und Setter für Status, da die Methode getStatusFromApi(String feedbackID) in StatusPageController.java nicht funktioniert
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

