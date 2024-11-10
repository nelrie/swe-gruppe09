package feedback;

import feedback.domain.Feedback;
import feedback.repository.FeedbackRepository;
import feedback.repository.MockFeedbackRepository;
import feedback.service.FeedbackService;
import feedback.domain.Status;
import feedback.service.StatusService;

public class Main {
    public static void main(String[] args) {

        // FeedbackRep erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        // Initialisierung des FeedbackService mit dem Feedbackrep
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);
        Feedback gespeichertesFeedback = null;

        try {
            // Feedback speichern
            gespeichertesFeedback = feedbackService.erstelleFeedback("Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");
            System.out.println("Das Feedback wurde erfolgreich erstellt: " + gespeichertesFeedback);

            // Feedback finden
            Feedback gefundenesFeedback = feedbackService.findeFeedback(gespeichertesFeedback.getFeedbackID());

            if (gefundenesFeedback != null)
            { System.out.println("Das Feedback wurde gefunden: " + gefundenesFeedback);
            }
            else
            { System.err.println("Das Feedback konnte nicht gefunden werden.");
            }

            // Feedback löschen
            feedbackService.loescheFeedback(gespeichertesFeedback.getFeedbackID());
            System.out.println("Das Feedback wurde gelöscht.");

            // Überprüfen, ob das Feedback erfolgreich gelöscht wurde
            gefundenesFeedback = feedbackService.findeFeedback(gespeichertesFeedback.getFeedbackID());
            if (gefundenesFeedback == null) { System.out.println("Das Feedback wurde erfolgreich gelöscht.");
            }
            else { System.err.println("Das Feedback konnte nicht gelöscht werden.");
            }
        } catch (Exception e)
        { System.err.println("Fehler: " + e.getMessage());
        }

//                feedbackService.loescheFeedback("1");
//                System.out.println("Das Feedback wurde entfernt: " + feedbackService.findeFeedback("1"));
//            } catch (Exception e) {
//                System.err.println("Fehler beim Löschen des Feedbacks: " + e.getMessage());
//            }


        StatusService feedbackUser = new StatusService("Max Mustermann");

        // Standard-Status anzeigen
        System.out.println("Aktueller Status: " + feedbackUser.getStatus().getDescription());

        // Status auf "in Bearbeitung" ändern
        feedbackUser.setStatus(Status.IN_PROGRESS);

        // Status auf "abgeschlossen" ändern
        feedbackUser.setStatus(Status.COMPLETED);

        // Status-Update verschicken
        feedbackUser.sendStatusUpdate();
    }
}
