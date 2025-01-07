package feedback.application.service;

import java.util.List;
import java.util.UUID;

import feedback.exceptions.validation.IdGenerator;
import feedback.infrastructure.repository.FeedbackRepository;
import feedback.domain.model.Feedback;
import feedback.exceptions.validation.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import status.application.service.StatusService;
import status.domain.model.Status;


@Service
public class FeedbackService {


    private static final Logger logger = LoggerFactory.getLogger(FeedbackService.class);


    @Autowired
    private final FeedbackRepository feedbackRepository;

    @Autowired
    private final StatusService statusService;

    // Konstruktor
    @Autowired //Vorschlag LLM
    public FeedbackService(FeedbackRepository feedbackRepository, StatusService statusService) {
        this.feedbackRepository = feedbackRepository;
        this.statusService = statusService;

    }


    // Nach GitHub Copilot und Analyse der Metriken wurde die Methode wie folgt geändert:

    public Feedback erstelleFeedback(String firstName, String lastName, String email, String message) {

        // Validierung der Eingaben
        validateInput(firstName, lastName, email, message);

        // Erzeugt eine zufällige ID -> vermeidet doppelte IDs
        String feedbackID = IdGenerator.generateShortUuid();

        //Erstellt ein neues Feedback Objekt mit der generierten ID
        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message);


        //Speicher das Feedback im Repository
        feedbackRepository.save(feedback);

        // Setzt und speichert den initialen Status
        statusService.setInitialStatus(feedbackID);
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

// Übung 7: Vorher
    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);

        logger.info("findeFeedback wird ausgeführt");
        logger.info("Feedback: {}", feedback); // Testen was in feedback gespeichert ist

        if (feedback == null) {
            throw new IllegalArgumentException("Das Feedback konnte nicht gefunden werden.");
        }
        else
            logger.info("Feedback von: {} {} {} {}", feedback.getFirstName(), feedback.getLastName(), feedback.getEmail(), feedback.getMessage());
        return feedback;
    }

     /* Übung 7: Nachher funktionalem Stil mit funktionalen Interfaces
    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findAll().stream()
            .filter(f -> f.getFeedbackID().equals(feedbackID))
            .findFirst()
            .orElse(null);
     }
     */

    //Übung 7: Vorher
    public void loescheFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback mit der ID " + feedbackID + " konnte nicht gefunden werden.");
        }
        feedbackRepository.deleteById(feedbackID);
    }
    
 /* Übung 7: Nachher funktionaler Stil mit Verwendung von Optional<T>

    public Optional<Feedback> loescheFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
        if (feedback == null) {
            return Optional.empty();
        }
        feedbackRepository.deleteById(feedbackID);
        return Optional.of(feedback);
    }
     */

    // Übung 7: Vorher
    public List<Feedback> findeAlleFeedbacks() {
        return feedbackRepository.findAll();
    }
/* Übung 7: Nachher funktionaler Stil mit StreamAPI

  public List<Feedback> findeAlleFeedbacks() {
        return feedbackRepository.findAll().stream()
             .filter(feedback -> feedback.getStatus() == Status.RECEIVED)
             .collect(Collectors.toList());
 */


}
