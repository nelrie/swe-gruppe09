package feedback.service;

import feedback.repository.FeedbackRepository;
import feedback.domain.Feedback;
import feedback.validation.InputValidator;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private long idCounter = 0; //zählt die ID hoch

    // Konstruktor
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Methode
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

        String feedbackID = String.valueOf(idCounter++);
        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message);

        feedbackRepository.save(feedback);
        return feedback;
    }

    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
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