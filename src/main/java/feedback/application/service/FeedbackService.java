package feedback.application.service;

import feedback.application.commands.CreateFeedbackCommand;
import feedback.domain.events.FeedbackAngelegtEvent;
import feedback.domain.model.Feedback;
import feedback.domain.valueobjects.Email;
import feedback.exceptions.validation.IdGenerator;
import feedback.infrastructure.repository.FeedbackRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import status.application.service.StatusService;
import status.domain.model.Status;

import java.util.List;


@Service
public class FeedbackService {


    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);


    @Autowired
    private final FeedbackRepository feedbackRepository;

    @Autowired
    private final StatusService statusService;

    @Autowired
    private final ApplicationEventPublisher eventPublisher;

    // Konstruktor
    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, StatusService statusService, ApplicationEventPublisher eventPublisher) {
        this.feedbackRepository = feedbackRepository;
        this.statusService = statusService;
        this.eventPublisher = eventPublisher;

    }


    // Übung 7: Nachher - Feedback-Erstellung mit funktionalem Interface
    public Feedback erstelleFeedback(CreateFeedbackCommand command) {
       // validateInput(firstName, lastName, email, message);

        String feedbackID = IdGenerator.generateShortUuid();
        Feedback feedback = new Feedback(feedbackID, command.fullName(), command.email(), command.message());

        Optional.of(feedback)
                .ifPresent(feedbackRepository::save); // Method-Referenz

        statusService.setInitialStatus(feedbackID);

        // Event veröffentlichen
        FeedbackAngelegtEvent event = new FeedbackAngelegtEvent(feedbackID, command.fullName(), command.email(), command.message());
        eventPublisher.publishEvent(event);
        return feedback;
    }


    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);

        logger.info("findeFeedback wird ausgeführt");
        logger.info("Feedback: {}", feedback); // Testen was in feedback gespeichert ist

        if (feedback == null) {
            throw new IllegalArgumentException("Das Feedback konnte nicht gefunden werden.");
        }
        else
            logger.info("Feedback von: {} {} {}", feedback.getFullName(), feedback.getEmail(), feedback.getMessage());
        return feedback;
    }

    /*Übung 7: Vorher
    public void loescheFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback mit der ID " + feedbackID + " konnte nicht gefunden werden.");
        }
        feedbackRepository.deleteById(feedbackID);
    }
    */

 //Übung 7: Nachher funktionaler Stil mit Verwendung von Optional<T>

    public Optional<Feedback> loescheFeedback(String feedbackID) {
        return Optional.ofNullable(feedbackRepository.findById(feedbackID))
                .map(feedback -> {
                    feedbackRepository.deleteById(feedbackID);
                    return feedback;
                });
    }

    /* Übung 7: Vorher
    public List<Feedback> findeAlleFeedbacks() {
        return feedbackRepository.findAll();
    }
    */

// Übung 7: Nachher funktionaler Stil mit StreamAPI

  public List<Feedback> findeAlleFeedbacks() {
        return feedbackRepository.findAll().stream()
                .filter(feedback -> feedback.getStatus() == Status.RECEIVED)
                .collect(Collectors.toList());


    }

    //Feedback nach Anzahl des Status anzeigen
    public Map<Status, Long> statistikFeedbackProStatus() {
        // Abrufen aller Feedbacks
        List<Feedback> feedbacks = feedbackRepository.findAll();

        // Null- oder Leere-Prüfung
        if (feedbacks == null || feedbacks.isEmpty()) {
            logger.warn("Es wurden keine Feedbacks gefunden.");
            return Map.of(); // Gibt eine leere, unveränderliche Map zurück
        }

        // Gruppieren und Zählen nach Status
        Map<Status, Long> statistik = feedbacks.stream()
                .collect(Collectors.groupingBy(
                        Feedback::getStatus,   // Gruppierungsschlüssel: Status des Feedbacks
                        Collectors.counting()  // Zählfunktion
                ));

        // Logging für Debugging
        statistik.forEach((status, count) -> logger.info("Status: {}, Anzahl: {}", status, count));

        return statistik; // Rückgabe der Statistik
    }

    // E-Mail alphabetisch anzeigen
    public List<String> getAllEmails() {
        // Alle Feedbacks aus dem Repository abrufen
        List<Feedback> feedbacks = feedbackRepository.findAll();

        // Null- oder Leerprüfung
        if (feedbacks == null || feedbacks.isEmpty()) {
            logger.warn("Keine Feedbacks gefunden.");
            return List.of(); // Gibt eine leere Liste zurück
        }

        // Informationen loggen: Anzahl der Feedbacks
        logger.info("Anzahl der abgerufenen Feedbacks: {}", feedbacks.size());

        // E-Mail-Adressen extrahieren, sortieren und zurückgeben
        return feedbacks.stream()
                .map(Feedback::getEmail)        // Nur die E-Mail-Adressen extrahieren
                .map(Email::toString)           // Sicherstellen, dass die E-Mail ein String ist
                .distinct()                     // Duplikate entfernen
                .sorted()                       // Alphabetisch sortieren
                .collect(Collectors.toList());  // Ergebnisse in eine Liste sammeln
    }
}
