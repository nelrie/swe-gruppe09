package feedback;

import feedback.domain.Feedback;
import feedback.repository.FeedbackRepository;
import feedback.repository.MockFeedbackRepository;
import feedback.service.FeedbackService;

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


    }
}
