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

        try{
        // Feedback speichern
        Feedback gespeichertesFeedback = feedbackService.erstelleFeedback("Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");

        System.out.println("Das Feedback wurde erfolreich erstellt: " + gespeichertesFeedback);
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern deines Feedbacks: " + e.getMessage());
        }

        try {

            // Feedback finden
            Feedback gespeicherteFeedbackID = feedbackService.findeFeedback("1");
            System.out.println("Das Feedback wurde gefunden: " + gespeicherteFeedbackID);
        } catch (Exception e) {
            System.err.println("Das Feedback konnte nicht gefunden werden: " + e.getMessage());
        }

        try{
            //Feedback löschen
            feedbackService.loescheFeedback("1");
            System.out.println("Das Feedback wurde entfernt: " + feedbackService.findeFeedback("1"));
        } catch (Exception e) {
            System.err.println("Fehler beim Löschen des Feedbacks: " + e.getMessage());
        }

        StatusService feedbackUser = new StatusService("Max Mustermann");

        // Den Standard-Status anzeigen
        System.out.println("Aktueller Status: " + feedbackUser.getStatus().getDescription());

        // Status auf "in Bearbeitung" ändern
        feedbackUser.setStatus(Status.IN_PROGRESS);

        // Status auf "abgeschlossen" ändern
        feedbackUser.setStatus(Status.COMPLETED);

        // Status-Update verschicken
        feedbackUser.sendStatusUpdate();
    }
}
