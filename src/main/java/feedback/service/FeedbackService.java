package feedback.service;

import java.util.UUID;

import feedback.domain.Status;
import feedback.repository.FeedbackRepository;
import feedback.domain.Feedback;
import feedback.validation.InputValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);

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

        //String feedbackID = String.valueOf(idCounter++);
        String feedbackID = UUID.randomUUID().toString(); //Erzeugt eine zufällige ID -> vermeidet doppelte IDs

        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message, Status.RECEIVED);

        feedbackRepository.save(feedback);
        return feedback;
    }

    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);

        logger.info("findeFeedback wird ausgeführt");
        logger.debug("Feedback: {}", feedback); // Testen was in feedback gespeichert ist

        if (feedback == null) {
            throw new IllegalArgumentException("Das Feedback konnte nicht gefunden werden.");
        }
        else
            logger.info("Feedback von: {} {} {} {}", feedback.getFirstName(), feedback.getLastName(), feedback.getEmail(), feedback.getMessage());
        return feedback;
    }

    public void loescheFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback mit der ID " + feedbackID + " konnte nicht gefunden werden.");
        }
        feedbackRepository.deleteById(feedbackID);
    }


    // Feedback nach Status gruppieren
    public Map<Status, Long> statistikFeedbackProStatus() {
        // Abrufen aller Feedbacks aus dem Repository
        List<Feedback> feedbacks = feedbackRepository.findAll();

        // Null oder leere Liste prüfen
        if (feedbacks == null || feedbacks.isEmpty()) {
            logger.warn("Es wurden keine Feedbacks gefunden.");
            return Map.of(); // Gibt eine leere Map zurück
        }

        // Debug-Ausgabe aller geladenen Feedbacks
        feedbacks.forEach(f -> logger.info("Feedback ID: {}, Status: {}", f.getFeedbackID(), f.getStatus()));

        // Gruppierung und Zählung der Feedbacks nach Status
        Map<Status, Long> statistik = feedbacks.stream()
                .collect(Collectors.groupingBy(
                        Feedback::getStatus,   // Gruppieren nach Status (Key)
                        Collectors.counting()  // Anzahl der Feedbacks je Status zählen (Value)
                ));

        // Logging der Ergebnisse
        statistik.forEach((status, count) -> logger.info("Status: {}, Anzahl: {}", status, count));

        return statistik;
    }

    // E-Mail alphabetisch anzeigen
    public List<String> getAllEmails() {
        // Alle Feedbacks aus dem Repository abrufen
        List<Feedback> feedbacks = feedbackRepository.findAll();

        // Informationen loggen: Anzahl der Feedbacks
        logger.info("Anzahl der abgerufenen Feedbacks: {}", feedbacks.size());

        // Stream nutzen, um die E-Mail-Adressen zu extrahieren, zu sortieren und Duplikate zu entfernen
        return feedbacks.stream()
                .map(Feedback::getEmail)        // Nur die E-Mail-Adressen extrahieren
                .distinct()                     // Duplikate entfernen
                .sorted()                       // Alphabetisch sortieren
                .collect(Collectors.toList());  // Ergebnisse in eine Liste sammeln
    }

}