package feedback.service;

import feedback.respository.FeedbackRepository;
import feedback.domain.Feedback;
import feedback.validation.InputValidator;

public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private long idCounter = 1; //zählt die ID hoch

    // Konstruktor
    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    // Methode
    public Feedback erstelleFeedback(String feedbackID, String firstName, String lastName, String email, String message) {
        feedbackID = String.valueOf(idCounter++);
        Feedback feedback = new Feedback(feedbackID, firstName, lastName, email, message);

        feedbackRepository.save(feedback);

        // Validierung der Eingaben
        if (!InputValidator.isValidFirstName(feedback.getFirstName())) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (!InputValidator.isValidLastName(feedback.getLastName())) {
            throw new IllegalArgumentException("Invalid last name");
        }
        if (!InputValidator.isValidEmail(feedback.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        if (!InputValidator.isValidMessage(message)) {
            throw new IllegalArgumentException("Nachricht darf nicht leer sein");
        }

        return feedback;
    }
    public Feedback findeFeedback(String feedbackID) {
        Feedback feedback = feedbackRepository.findById(feedbackID);
        if (feedback == null) {
            throw new IllegalArgumentException("Feedback not found");
        }
        else
            System.out.println(("Feedback von: " + feedback.getFirstName() + " " + feedback.getLastName() + " " + feedback.getEmail()));
        return feedback;
    }




}