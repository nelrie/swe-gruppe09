package feedback.application.service;

import java.util.List;
import java.util.UUID;
import feedback.infrastructure.repository.FeedbackRepository;
import feedback.domain.model.Feedback;
import feedback.exceptions.validation.InputValidator;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    // Konstruktor
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }


    // Nach GitHub Copilot und Analyse der Metriken wurde die Methode wie folgt geändert:

    public Feedback erstelleFeedback(String firstName, String lastName, String email, String message) {

        // Validierung der Eingaben
        validateInput(firstName, lastName, email, message);

        // Erzeugt eine zufällige ID -> vermeidet doppelte IDs
        String feedbackID = UUID.randomUUID().toString();

        //Erstellt ein neues Feedback Objekt mit der generierten ID
        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message);


        //Speicher das Feedback im Repository
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

    public List<Feedback> findeAlleFeedbacks() {
        return feedbackRepository.findAll();
    }
}