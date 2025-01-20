package feedback.domain.valueobjects;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private static final Logger logger = LoggerFactory.getLogger(MessageTest.class);

    // Überprüft, ob ein gültiges Message-Objekt erstellt wurde
    @Test
    void testValidMessageCreation() {
        // Arrange
        String validMessage = "Dies ist eine gültige Nachricht.";
        logger.info("Test gestartet: Erstellen einer gültigen Nachricht ('{}').", validMessage);

        // Act
        Message message = new Message(validMessage);

        // Assert
        assertNotNull(message, "Das Message-Objekt sollte nicht null sein.");
        assertEquals(validMessage, message.getMessageInput(), "Die Nachricht sollte korrekt gespeichert werden.");
        logger.info("SUCCESS: Gültige Nachricht wurde erfolgreich erstellt.");
    }


    @Test
    void testEmptyMessageThrowsException() {
        // Arrange
        String emptyMessage = "      ";
        logger.info("Test gestartet: Versuch, eine leere Nachricht (nur Leerzeichen) zu erstellen.");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Message(emptyMessage));
        assertEquals("Nachricht darf nicht leer sein", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für leere Nachricht korrekt geworfen. Nachricht: {}", exception.getMessage());
    }

    @Test
    void testNullMessageThrowsException() {
        // Arrange
        String nullMessage = null;
        logger.info("Test gestartet: Versuch, eine null-Nachricht zu erstellen.");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Message(nullMessage));
        assertEquals("Nachricht darf nicht leer sein", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für null-Nachricht korrekt geworfen. Nachricht: {}", exception.getMessage());
    }

    @Test
    void testEquality() {
        // Arrange
        String messageInput = "Dies ist eine Nachricht.";
        logger.info("Test gestartet: Vergleich zweier Nachrichten mit dem gleichen Inhalt ('{}').", messageInput);

        Message message1 = new Message(messageInput);
        Message message2 = new Message(messageInput);

        // Act & Assert
        assertEquals(message1, message2, "Zwei Nachrichten mit dem gleichen Inhalt sollten gleich sein.");
        logger.info("SUCCESS: Beides Nachrichten wurden als gleich erkannt.");
    }

    @Test
    void testInequality() {
        // Arrange
        String messageInput1 = "Dies ist die erste Nachricht.";
        String messageInput2 = "Dies ist eine andere Nachricht.";
        logger.info("Test gestartet: Vergleich zweier Nachrichten mit unterschiedlichem Inhalt ('{}', '{}').", messageInput1, messageInput2);

        Message message1 = new Message(messageInput1);
        Message message2 = new Message(messageInput2);

        // Act & Assert
        assertNotEquals(message1, message2, "Zwei Nachrichten mit unterschiedlichen Inhalten sollten nicht gleich sein.");
        logger.info("SUCCESS: Beides Nachrichten wurden korrekt als ungleich erkannt.");
    }

    @Test
    void testToString() {
        // Arrange
        String messageInput = "Dies ist eine Nachricht.";
        logger.info("Test gestartet: Test für die toString()-Methode ('{}').", messageInput);

        Message message = new Message(messageInput);

        // Act
        String result = message.toString();

        // Assert
        assertEquals(messageInput, result, "Die toString()-Methode sollte den Nachrichteninhalt zurückgeben.");
        logger.info("SUCCESS: Die toString()-Methode funktionierte wie erwartet. Ergebnis: '{}'.", result);
    }

    @Test
    void testEqualsWithNull() {
        // Arrange
        String messageInput = "Dies ist eine Nachricht.";
        logger.info("Test gestartet: Vergleich einer Nachricht mit null.");

        Message message = new Message(messageInput);

        // Act & Assert
        assertNotEquals(message, null, "Eine Nachricht sollte nicht gleich null sein.");
        logger.info("SUCCESS: Nachricht wurde korrekt als ungleich zu null erkannt.");
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        String messageInput = "Dies ist eine Nachricht.";
        logger.info("Test gestartet: Vergleich einer Nachricht mit einem Objekt einer anderen Klasse.");

        Message message = new Message(messageInput);
        String differentObject = "Dies ist keine Nachricht.";

        // Act & Assert
        assertNotEquals(message, differentObject, "Eine Nachricht sollte nicht gleich einem Objekt einer anderen Klasse sein.");
        logger.info("SUCCESS: Nachricht wurde korrekt als ungleich zu einem Objekt einer anderen Klasse erkannt.");
    }

    @Test
    void testHashCodeEquality() {
        // Arrange
        String messageInput = "Dies ist eine Nachricht.";
        logger.info("Test gestartet: Vergleich von hashCode() für zwei gleiche Nachrichten ('{}').", messageInput);

        Message message1 = new Message(messageInput);
        Message message2 = new Message(messageInput);

        // Act & Assert
        assertEquals(message1.hashCode(), message2.hashCode(), "Zwei gleiche Nachrichten sollten denselben Hashcode haben.");
        logger.info("SUCCESS: Beide Nachrichten haben denselben Hashcode.");
    }

    @Test
    void testHashCodeInequality() {
        // Arrange
        String messageInput1 = "Nachricht 1";
        String messageInput2 = "Nachricht 2";
        logger.info("Test gestartet: Vergleich von hashCode() für zwei unterschiedliche Nachrichten ('{}', '{}').", messageInput1, messageInput2);

        Message message1 = new Message(messageInput1);
        Message message2 = new Message(messageInput2);

        // Act & Assert
        assertNotEquals(message1.hashCode(), message2.hashCode(), "Zwei unterschiedliche Nachrichten sollten unterschiedliche Hashcodes haben.");
        logger.info("SUCCESS: Nachrichten mit unterschiedlichem Inhalt haben unterschiedliche Hashcodes.");
    }
}
