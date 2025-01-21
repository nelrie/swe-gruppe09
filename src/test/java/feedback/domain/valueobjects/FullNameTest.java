package feedback.domain.valueobjects;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.jupiter.api.Assertions.*;

class FullNameTest {

    private static final Logger logger = LoggerFactory.getLogger(FullNameTest.class);


    // Überprüft die korrekte Erstellung gültiger Namen.
    @Test
    void testValidFullNameCreation() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        logger.info("Test gestartet: Vollständiger Name mit gültigen Eingaben ('{}', '{}').", firstName, lastName);

        // Act
        FullName fullName = new FullName(firstName, lastName);

        // Assert
        assertNotNull(fullName, "Das FullName-Objekt sollte nicht null sein.");
        assertEquals(firstName, fullName.getFirstName(), "Der Vorname sollte korrekt gespeichert werden.");
        assertEquals(lastName, fullName.getLastName(), "Der Nachname sollte korrekt gespeichert werden.");
        logger.info("SUCCESS: Vollständiger Name wurde mit den erwarteten Werten erstellt.");
    }

    @Test
    void testNullFirstNameThrowsException() {
        // Arrange
        String firstName = null;
        String lastName = "Doe";
        logger.info("Test gestartet: Null als Vorname und gültiger Nachname ('{}').", lastName);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FullName(firstName, lastName));
        assertEquals("Ungültiger Vorname: darf nicht null oder leer sein", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für null-Vorname korrekt geworfen. Nachricht: {}", exception.getMessage());
    }

    @Test
    void testEmptyFirstNameThrowsException() {
        // Arrange
        String firstName = "   ";
        String lastName = "Doe";
        logger.info("Test gestartet: Leerer Vorname (nur Leerzeichen) und gültiger Nachname ('{}').", lastName);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FullName(firstName, lastName));
        assertEquals("Ungültiger Vorname: darf nicht null oder leer sein", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für leeren Vorname korrekt geworfen. Nachricht: {}", exception.getMessage());
    }


    // leere Werte
    @Test
    void testNullLastNameThrowsException() {
        // Arrange
        String firstName = "John";
        String lastName = null;
        logger.info("Test gestartet: Gültiger Vorname ('{}') und Null als Nachname.", firstName);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FullName(firstName, lastName));
        assertEquals("Ungültiger Nachname: darf nicht null oder leer sein", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für null-Nachname korrekt geworfen. Nachricht: {}", exception.getMessage());
    }

    @Test
    void testEmptyLastNameThrowsException() {
        // Arrange
        String firstName = "John";
        String lastName = "   ";
        logger.info("Test gestartet: Gültiger Vorname ('{}') und leerer Nachname (nur Leerzeichen).", firstName);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FullName(firstName, lastName));
        assertEquals("Ungültiger Nachname: darf nicht null oder leer sein", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für leeren Nachname korrekt geworfen. Nachricht: {}", exception.getMessage());
    }


    // ungültige Zeichen
    @Test
    void testInvalidFirstNameThrowsException() {
        // Arrange
        String firstName = "J@hn";
        String lastName = "Doe";
        logger.info("Test gestartet: Ungültiger Vorname ('{}') und gültiger Nachname ('{}').", firstName, lastName);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FullName(firstName, lastName));
        assertEquals("Ungültiger Vorname: enthält unzulässige Zeichen", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für ungültigen Vornamen richtig geworfen. Nachricht: {}", exception.getMessage());
    }

    @Test
    void testInvalidLastNameThrowsException() {
        // Arrange
        String firstName = "John";
        String lastName = "D0e";
        logger.info("Test gestartet: Gültiger Vorname ('{}') und ungültiger Nachname ('{}').", firstName, lastName);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new FullName(firstName, lastName));
        assertEquals("Ungültiger Nachname: enthält unzulässige Zeichen", exception.getMessage(), "Die Fehlermeldung sollte korrekt sein.");
        logger.info("SUCCESS: Ausnahme für ungültigen Nachnamen richtig geworfen. Nachricht: {}", exception.getMessage());
    }


    // Prüft, ob zwei gleiche Objekte als gleich erkannt werden.
    @Test
    void testEquality() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        logger.info("Test gestartet: Gleichheit zweier FullName-Objekte ('{}', '{}').", firstName, lastName);

        FullName fullName1 = new FullName(firstName, lastName);
        FullName fullName2 = new FullName(firstName, lastName);

        // Act & Assert
        assertEquals(fullName1, fullName2, "Zwei FullName-Objekte mit denselben Werten sollten gleich sein.");
        logger.info("SUCCESS: Beide FullName-Objekte wurden als gleich erkannt.");
    }


    // Prüft, ob unterschiedliche Objekte als ungleich erkannt werden.
    @Test
    void testInequality() {
        // Arrange
        String firstName1 = "John";
        String lastName1 = "Doe";
        String firstName2 = "Jane";
        String lastName2 = "Smith";
        logger.info("Test gestartet: Ungleichheit zweier FullName-Objekte ('{}', '{}') und ('{}', '{}').", firstName1, lastName1, firstName2, lastName2);

        FullName fullName1 = new FullName(firstName1, lastName1);
        FullName fullName2 = new FullName(firstName2, lastName2);

        // Act & Assert
        assertNotEquals(fullName1, fullName2, "Zwei FullName-Objekte mit unterschiedlichen Werten sollten nicht gleich sein.");
        logger.info("SUCCESS: Beide FullName-Objekte wurden korrekt als ungleich erkannt.");
    }

    // Validiert die Ausgabe der toString()-Methode
    @Test
    void testToString() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        logger.info("Test gestartet: Test für toString()-Methode ('{}', '{}').", firstName, lastName);

        FullName fullName = new FullName(firstName, lastName);

        // Act
        String result = fullName.toString();

        // Assert
        assertEquals("John Doe", result, "Die toString()-Methode sollte den vollständigen Namen im Format 'Vorname Nachname' zurückgeben.");
        logger.info("SUCCESS: Die toString()-Methode funktionierte wie erwartet. Ergebnis: '{}'.", result);
    }

    @Test
    void testEqualityWithSameObject() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        logger.info("Test gestartet: Vergleich eines Objekts mit sich selbst ('{}', '{}').", firstName, lastName);

        FullName fullName = new FullName(firstName, lastName);

        // Act & Assert
        assertEquals(fullName, fullName, "Ein FullName-Objekt sollte sich selbst als gleich erkennen.");
        logger.info("SUCCESS: Ein Objekt wurde korrekt als gleich zu sich selbst erkannt.");
    }

    @Test
    void testEqualityWithNull() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        logger.info("Test gestartet: Vergleich eines Objekts mit null ('{}', '{}').", firstName, lastName);

        FullName fullName = new FullName(firstName, lastName);

        // Act & Assert
        assertNotEquals(null, fullName, "Ein FullName-Objekt sollte nicht gleich null sein.");
        logger.info("SUCCESS: Ein Objekt wurde korrekt als ungleich zu null erkannt.");
    }

    @Test
    void testEqualityWithDifferentClass() {
        // Arrange
        String firstName = "John";
        String lastName = "Doe";
        logger.info("Test gestartet: Vergleich eines Objekts mit einer Instanz einer anderen Klasse ('{}', '{}').", firstName, lastName);

        FullName fullName = new FullName(firstName, lastName);
        String otherObject = "Dies ist ein String und kein FullName";

        // Act & Assert
        assertNotEquals(fullName, otherObject, "Ein FullName-Objekt sollte nicht gleich einem Objekt einer anderen Klasse sein.");
        logger.info("SUCCESS: Ein Objekt wurde korrekt als ungleich zu einem Objekt einer anderen Klasse erkannt.");
    }

    // Überprüfung des Hashcode für gleiche und unterschiedliche FullName-Objekte
    @Test
    void testHashCode() {
        // Arrange
        String firstName1 = "John";
        String lastName1 = "Doe";
        String firstName2 = "Jane";
        String lastName2 = "Doe";
        logger.info("Test gestartet: Überprüfung von hashCode() für verschiedene FullName-Objekte.");

        FullName fullName1 = new FullName(firstName1, lastName1);
        FullName fullName2 = new FullName(firstName1, lastName1); // Gleiches Objekt wie fullName1
        FullName fullName3 = new FullName(firstName2, lastName2); // Unterschiedliches Objekt

        // Act & Assert
        assertEquals(fullName1.hashCode(), fullName2.hashCode(), "Hashcodes von gleichen FullName-Objekten sollten identisch sein.");
        assertNotEquals(fullName1.hashCode(), fullName3.hashCode(), "Hashcodes von unterschiedlichen FullName-Objekten sollten verschieden sein.");
        logger.info("SUCCESS: Hashcodes wurden korrekt überprüft.");
    }
}