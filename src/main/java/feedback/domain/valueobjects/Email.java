package feedback.domain.valueobjects;

import feedback.exceptions.validation.InputValidator;

import java.util.Objects;

public class Email {
    private final String emailInput;

    public Email(String emailInput) {
        if (emailInput == null || !InputValidator.isValidEmail(emailInput)) {
            throw new IllegalArgumentException("Ungültige E-Mail-Adresse");
        }
        this.emailInput = emailInput;
    }

    public String getEmailInput() {
        return emailInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(emailInput, email.emailInput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailInput);
    }

    @Override
    public String toString() {
        return emailInput;
    }
}
