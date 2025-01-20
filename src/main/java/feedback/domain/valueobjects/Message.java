package feedback.domain.valueobjects;

import feedback.exceptions.validation.InputValidator;

import java.util.Objects;

public class Message {
    private final String messageInput;

    public Message(String messageInput) {
        if (!InputValidator.isValidMessage(messageInput)) {
            throw new IllegalArgumentException("Nachricht darf nicht leer sein");
        }
        this.messageInput = messageInput;
    }

    public String getMessageInput() {
        return messageInput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return messageInput.equals(message.messageInput);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageInput);
    }

    @Override
    public String toString() {
        return messageInput;
    }
}
