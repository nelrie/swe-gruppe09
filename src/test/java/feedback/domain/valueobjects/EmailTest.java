package feedback.domain.valueobjects;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private static final Logger logger = LoggerFactory.getLogger(EmailTest.class);

    @Test
    void testValidEmail() {
        // Arrange
        String validEmail = "test@example.com";
        logger.info("Test für gültige E-Mail gestartet. Erwartet wird: {}", validEmail);

        // Act
        Email email = new Email(validEmail);
        logger.info("E-Mail-Objekt erfolgreich erstellt: {}", email.getEmailInput());

        // Assert
        assertEquals(validEmail, email.getEmailInput());
        logger.info("SUCCESS: Gültige E-Mail wurde korrekt verarbeitet.");
    }

    @Test
    void testInvalidEmailThrowsException() {
        // Arrange
        String invalidEmail = "invalid-email";
        logger.info("Test für ungültige E-Mail gestartet. Erwartet wird: IllegalArgumentException für: {}", invalidEmail);
        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
        logger.info("Ausnahme gefangen. Nachricht: {}", exception.getMessage());
        assertEquals("Ungültige E-Mail-Adresse", exception.getMessage());
        logger.info("SUCCESS: Ungültige E-Mail wurde korrekt als Fehler erkannt.");
    }

    @Test
    void testNullEmailThrowsException() {
        logger.info("Test für null-E-Mail gestartet. Erwartet wird: IllegalArgumentException.");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Email(null));
        logger.info("Ausnahme gefangen. Nachricht: {}", exception.getMessage());
        assertEquals("Ungültige E-Mail-Adresse", exception.getMessage());
        logger.info("SUCCESS: Null-Wert wurde erfolgreich als ungültige Eingabe erkannt.");
    }

    @Test
    void testEmailEquality() {
        // Arrange
        String emailString = "test@example.com";
        logger.info("Test für E-Mail-Gleichheit gestartet. Erwartet wird Gleichheit der E-Mails: {}", emailString);
        // Act
        Email email1 = new Email(emailString);
        Email email2 = new Email(emailString);

        logger.info("Email-Objekt 1: {}", email1.getEmailInput());
        logger.info("Email-Objekt 2: {}", email2.getEmailInput());

        // Assert
        assertEquals(email1, email2);
        logger.info("SUCCESS: Beide E-Mails sind gleich (wie erwartet).");
    }

        @Test
        void testEqualsWithSameObject() {
            Email email = new Email("test@example.com");
            assertEquals(email, email, "Ein Objekt sollte sich selbst als gleich ansehen.");
        }

        @Test
        void testEqualsWithEqualObjects() {
            Email email1 = new Email("test@example.com");
            Email email2 = new Email("test@example.com");
            assertEquals(email1, email2, "Zwei Email-Objekte mit denselben Werten sollten gleich sein.");
        }

        @Test
        void testEqualsWithDifferentObjects() {
            Email email1 = new Email("test@example.com");
            Email email2 = new Email("different@example.com");
            assertNotEquals(email1, email2, "Zwei Email-Objekte mit unterschiedlichen Werten sollten nicht gleich sein.");
        }

        @Test
        void testEqualsWithNull() {
            Email email = new Email("test@example.com");
            assertNotEquals(null, email, "Ein Email-Objekt sollte nicht gleich null sein.");
        }

        @Test
        void testEqualsWithDifferentClass() {
            Email email = new Email("test@example.com");
            String differentObject = "test@example.com"; // Kein E-Mail-Objekt
            assertNotEquals(email, differentObject, "Ein Email-Objekt sollte nicht gleich einem Objekt einer anderen Klasse sein.");
        }

        @Test
        void testHashCodeWithEqualObjects() {
            Email email1 = new Email("test@example.com");
            Email email2 = new Email("test@example.com");
            assertEquals(email1.hashCode(), email2.hashCode(), "Zwei gleiche Email-Objekte sollten denselben Hash-Code haben.");
        }

        @Test
        void testHashCodeWithDifferentObjects() {
            Email email1 = new Email("test@example.com");
            Email email2 = new Email("different@example.com");
            assertNotEquals(email1.hashCode(), email2.hashCode(), "Zwei unterschiedliche Email-Objekte sollten unterschiedliche Hash-Codes haben.");
        }
    }
