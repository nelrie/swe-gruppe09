package feedback;

import feedback.domain.Feedback;
import feedback.respository.FeedbackRepository;
import feedback.respository.MockFeedbackRepository;
import feedback.service.FeedbackService;

public class Main {
    public static void main(String[] args) {

        // FeedbackRep erstellen
        FeedbackRepository feedbackRepository = new MockFeedbackRepository();

        // Initialisierung des FeedbackService mit dem Feedbackrep
        FeedbackService feedbackService = new FeedbackService(feedbackRepository);

        try{
        // Feedback Objekt mit Beispielen
        Feedback feedback = new Feedback("1", "Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");

        // Feedback speichern
        Feedback gespeichertesFeedback = feedbackService.erstelleFeedback(feedback.getFeedbackID(), feedback.getFirstName(), feedback.getLastName(), feedback.getEmail(), feedback.getMessage());


        System.out.println("Das Feedback wurde erfolreich erstellt: " + gespeichertesFeedback);
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern deines Feedbacks: " + e.getMessage());
        }

    }
}
