package feedback.service;

import java.util.UUID;
import feedback.repository.FeedbackRepository;
import feedback.domain.Feedback;
import feedback.validation.InputValidator;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // Konstruktor
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Methode

    /*
    public Feedback erstelleFeedback( String firstName, String lastName, String email, String message) {

        // Validierung der Eingaben
        if (!InputValidator.isValidFirstName(firstName)){
            throw new IllegalArgumentException("Ungültiger Vorname");
        }
        if (!InputValidator.isValidLastName(lastName)) {
            throw new IllegalArgumentException("Ungültiger Nachname");
        }
        if (!InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Ungültige E-Mail-Adresse");
        }
        if (!InputValidator.isValidMessage(message)) {
            throw new IllegalArgumentException("Nachricht darf nicht leer sein");
        }

        //String feedbackID = String.valueOf(idCounter++);
        String feedbackID = UUID.randomUUID().toString(); //Erzeugt eine zufällige ID -> vermeidet doppelte IDs

        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message);

        feedbackRepository.save(feedback);
        return feedback;
    }
*/
    // Nach GitHub Copilot und Analyse der Metriken wurde die Methode wie folgt geändert:

    public Feedback erstelleFeedback(String firstName, String lastName, String email, String message) {

        // Validierung der Eingaben
        validateInput(firstName, lastName, email, message);

        String feedbackID = UUID.randomUUID().toString(); // Erzeugt eine zufällige ID -> vermeidet doppelte IDs

        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message);

        feedbackRepository.save(feedback);
        return feedback;
    }

    private void validateInput(String firstName, String lastName, String email, String message) {
        if (!InputValidator.isValidFirstName(firstName)) {
            throw new IllegalArgumentException("Ungültiger Vorname");
        }
        if (!InputValidator.isValidLastName(lastName)) {
            throw new IllegalArgumentException("Ungültiger Nachname");
        }
        if (!InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException("Ungültige E-Mail-Adresse");
        }
        if (!InputValidator.isValidMessage(message)) {
            throw new IllegalArgumentException("Nachricht darf nicht leer sein");
        }
    }


    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);

        System.out.println("findeFeedback wird ausgeführt");
        System.out.println("Feedback: " + feedback); // Testen was in feedback gespeichert ist

        if (feedback == null) {
            throw new IllegalArgumentException("Das Feedback konnte nicht gefunden werden.");
        }
        else
            System.out.println(("Feedback von: " + feedback.getFirstName() + " " + feedback.getLastName() + " " + feedback.getEmail() + " " +feedback.getMessage()));
        return feedback;
    }

    public void loescheFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback mit der ID " + feedbackID + " konnte nicht gefunden werden.");
        }
        feedbackRepository.deleteById(feedbackID);
    }
}