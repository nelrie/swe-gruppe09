package feedback.exceptions.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    @Test
    void testValidFirstName() {
        assertTrue(InputValidator.isValidName("John"));
        assertTrue(InputValidator.isValidName("John Doe"));
        assertTrue(InputValidator.isValidName("Lisa-Marie"));
        assertFalse(InputValidator.isValidName("John123"));
        assertFalse(InputValidator.isValidName(""));

        //Testen der Randfälle:
        
        assertFalse(InputValidator.isValidName("  "));
        assertFalse(InputValidator.isValidName("@"));
        assertFalse(InputValidator.isValidName("%*#"));
        assertFalse(InputValidator.isValidName("Lisa--Marie"));
        assertTrue(InputValidator.isValidName("Übermuth"));
        assertTrue(InputValidator.isValidName("Straße"));
        assertFalse(InputValidator.isValidName(" Lisa"));
        assertFalse(InputValidator.isValidName("Lisa "));
        
    }
    @Test
    void testValidLastName() {
        assertTrue(InputValidator.isValidName("Umlauf"));
        assertTrue(InputValidator.isValidName("John Doe"));
        assertTrue(InputValidator.isValidName("Lisa-Marie"));
        assertFalse(InputValidator.isValidName("John123"));
        assertFalse(InputValidator.isValidName(""));

        //Testen der Randfälle:

        assertFalse(InputValidator.isValidName("  "));
        assertFalse(InputValidator.isValidName("@"));
        assertFalse(InputValidator.isValidName("%*#"));
        assertFalse(InputValidator.isValidName("Lisa--Marie"));
        assertTrue(InputValidator.isValidName("Übermuth"));
        assertTrue(InputValidator.isValidName("Straße"));
        assertFalse(InputValidator.isValidName(" Lisa"));
        assertFalse(InputValidator.isValidName("Lisa "));

    }

    @Test
    void testValidEmail() {
        assertTrue(InputValidator.isValidEmail("john.doe@example.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@com"));
        assertFalse(InputValidator.isValidEmail("john.doe@.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@com."));

        //Testen der Randfälle:

        assertFalse(InputValidator.isValidEmail("  "));
        assertFalse(InputValidator.isValidEmail(""));
        assertFalse(InputValidator.isValidEmail(" john.doe@example.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@example.com "));

    }
}
