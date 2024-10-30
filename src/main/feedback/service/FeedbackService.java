package feedback.service;

import feedback.model.Feedback;
import feedback.validation.InputValidator;
import feedback.repository.FeedbackRepository;

public class FeedbackService {

    private FeedbackRepository feedbackRepository;

    public FeedbackService(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    public void submitFeedback(Feedback feedback) {
        if (!InputValidator.isValidFirstName(feedback.getFirstName())) {
            throw new IllegalArgumentException("Invalid first name");
        }
        if (!InputValidator.isValidLastName(feedback.getLastName())) {
            throw new IllegalArgumentException("Invalid last name");
        }
        if (!InputValidator.isValidEmail(feedback.getEmail())) {
            throw new IllegalArgumentException("Invalid email");
        }
        // Speichere das Feedback (Datenbankoperationen hier hinzufügen)
    }
}