package feedback;

import feedback.domain.Feedback;
import feedback.repository.FeedbackRepository;
import feedback.repository.MockFeedbackRepository;
import feedback.service.FeedbackService;
import feedback.domain.Status;
import feedback.service.StatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        // Spring-Anwendungskontext initialisieren
        ApplicationContext context = SpringApplication.run(Main.class, args);

        // FeedbackRep erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        // Initialisierung des FeedbackService mit dem Feedbackrep
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        Feedback gespeichertesFeedback = null;

        try {
            // Feedback speichern
            gespeichertesFeedback = feedbackService.erstelleFeedback("Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");
            logger.info("Das Feedback wurde erfolgreich erstellt: {}", gespeichertesFeedback);

            // Feedback finden
            Feedback gefundenesFeedback = feedbackService.findeFeedback(gespeichertesFeedback.getFeedbackID());

            if (gefundenesFeedback != null)
            { logger.info("Das Feedback wurde gefunden: {}" + gefundenesFeedback);
            }
            else
            { logger.warn("Das Feedback konnte nicht gefunden werden.");
            }

            // Feedback löschen
            feedbackService.loescheFeedback(gespeichertesFeedback.getFeedbackID());
            logger.info("Das Feedback wurde gelöscht.");

            // Überprüfen, ob das Feedback erfolgreich gelöscht wurde
            gefundenesFeedback = feedbackService.findeFeedback(gespeichertesFeedback.getFeedbackID());
            if (gefundenesFeedback == null) { logger.info("Das Feedback wurde erfolgreich gelöscht.");
            }
            else { logger.warn("Das Feedback konnte nicht gelöscht werden.");
            }
        } catch (Exception e)
        { logger.error("Fehler: {} ", e.getMessage());
        }

        // StatusService-Bean aus dem Kontext abrufen
        StatusService feedbackUser = context.getBean("feedbackStatusService",StatusService.class);

        // Standard-Status anzeigen
        logger.info("Aktueller Status: " + feedbackUser.getStatus().getDescription());

        // Status auf "in Bearbeitung" ändern
        feedbackUser.setStatus(Status.IN_PROGRESS);

        // Status auf "abgeschlossen" ändern
        feedbackUser.setStatus(Status.COMPLETED);

        // Status-Update verschicken
        feedbackUser.sendStatusUpdate();
    }
}


//        StatusService feedbackUser = new StatusService("Max Mustermann");

//        // Standard-Status anzeigen
//        logger.info("Aktueller Status: " + feedbackUser.getStatus().getDescription());
//
//        // Status auf "in Bearbeitung" ändern
//        feedbackUser.setStatus(Status.IN_PROGRESS);
//
//        // Status auf "abgeschlossen" ändern
//        feedbackUser.setStatus(Status.COMPLETED);
//
//        // Status-Update verschicken
//        feedbackUser.sendStatusUpdate();

