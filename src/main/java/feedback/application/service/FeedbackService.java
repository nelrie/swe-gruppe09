package feedback.application.service;

import feedback.application.commands.CreateFeedbackCommand;
import feedback.domain.events.FeedbackAngelegtEvent;
import feedback.domain.model.Feedback;
import feedback.exceptions.validation.IdGenerator;
import feedback.infrastructure.repository.FeedbackRepository;
import feedback.domain.model.Feedback;
import feedback.exceptions.validation.InputValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
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

/*
    public Feedback erstelleFeedback(CreateFeedbackCommand command) {
        // Erzeugt eine zufällige ID -> vermeidet doppelte IDs
        String feedbackID = IdGenerator.generateShortUuid();
        Feedback feedback = new Feedback(feedbackID, command.fullName(), command.email(), command.message());
        feedbackRepository.save(feedback);

        // Setzt und speichert den initialen Status
        statusService.setInitialStatus(feedbackID);

        // Erstellen und Veröffentlichen des Events
        FeedbackAngelegtEvent event = new FeedbackAngelegtEvent(feedbackID, command.fullName(), command.email(), command.message());
        eventPublisher.publishEvent(event);

        return feedback;
    }

 */

    // Übung 7: Nachher - Feedback-Erstellung mit funktionalem Interface
    public Feedback erstelleFeedback(CreateFeedbackCommand command) {
       // validateInput(firstName, lastName, email, message);

        String feedbackID = IdGenerator.generateShortUuid();
        Feedback feedback = new Feedback(feedbackID, command.fullName(), command.email(), command.message());

        Optional.of(feedback)
                .ifPresent(feedbackRepository::save); // Method-Referenz

        statusService.setInitialStatus(feedbackID);
        return feedback;
    }

//    private void validateInput(String firstName, String lastName, String email, String message) {
//        if (!InputValidator.isValidFirstName(firstName)) {
//            throw new IllegalArgumentException("Ungültiger Vorname");
//        }
//        if (!InputValidator.isValidLastName(lastName)) {
//            throw new IllegalArgumentException("Ungültiger Nachname");
//        }
//        if (!InputValidator.isValidEmail(email)) {
//            throw new IllegalArgumentException("Ungültige E-Mail-Adresse");
//        }
//        if (!InputValidator.isValidMessage(message)) {
//            throw new IllegalArgumentException("Nachricht darf nicht leer sein");
//        }
//
//    }

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

}
