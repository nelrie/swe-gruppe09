package feedback.service;
import feedback.domain.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("feedbackStatusService")

public class StatusService {
    private static final Logger logger = LoggerFactory.getLogger(StatusService.class);
    private Status status;
    private final String userStatus; // Konstruktor-injizierte Bean

    // Konstruktor-Injektion (empfohlene Methode für Pflicht-Beans)
@Autowired
    public StatusService(@Value("${status.service.user:DefaultUser}") String userStatus) {
        this.userStatus = userStatus;
        this.status = Status.RECEIVED; // Standard-Status

        // Log hinzufügen, um den tatsächlichen Klassentyp zu überprüfen
        logger.info("Tatsächliche Klassendefinition (Proxy-Check): {}", this.getClass().getName());
    }


    // Methode zum Ändern des Status
    public void setStatus(Status newStatus) {
        this.status = newStatus;
        logger.info("Der Status wurde auf '{}' geändert.", newStatus.getDescription());
    }

    // Methode, um den aktuellen Status abzurufen
    public Status getStatus() {
        return this.status;
    }

    // Methode zum "Verschicken" des Feedback-Status (simuliert)
    public void sendStatusUpdate() {
        logger.info("Der Benutzer '{}' hat den Feedback-Status verschickt: {}", userStatus, status.getDescription());

    }
}

