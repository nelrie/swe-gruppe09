package feedback.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputValidatorTest {

    @Test
    public void testValidFirstName() {
        assertTrue(InputValidator.isValidFirstName("John Doe"));
        assertTrue(InputValidator.isValidFirstName("Lisa-Marie"));
        assertFalse(InputValidator.isValidFirstName("John123"));
        assertFalse(InputValidator.isValidFirstName(""));

        //Testen der Randfälle:
        
        assertFalse(InputValidator.isValidFirstName("  "));
        assertFalse(InputValidator.isValidFirstName("@"));
        assertFalse(InputValidator.isValidFirstName("%*#"));
        assertFalse(InputValidator.isValidFirstName("Lisa--Marie"));
        assertTrue(InputValidator.isValidFirstName("Übermuth"));
        assertTrue(InputValidator.isValidFirstName("Straße"));
        assertFalse(InputValidator.isValidFirstName(" Lisa"));
        assertFalse(InputValidator.isValidFirstName("Lisa "));
        
    }
    @Test
    public void testValidLastName() {
        assertTrue(InputValidator.isValidLastName("John Doe"));
        assertTrue(InputValidator.isValidLastName("Lisa-Marie"));
        assertFalse(InputValidator.isValidLastName("John123"));
        assertFalse(InputValidator.isValidLastName(""));

        //Testen der Randfälle:
        
        assertFalse(InputValidator.isValidFirstName("  "));
        assertFalse(InputValidator.isValidFirstName("@"));
        assertFalse(InputValidator.isValidFirstName("%*#"));
        assertFalse(InputValidator.isValidFirstName("Lisa--Marie"));
        assertTrue(InputValidator.isValidFirstName("Übermuth"));
        assertTrue(InputValidator.isValidFirstName("Straße"));
        assertFalse(InputValidator.isValidFirstName(" Lisa"));
        assertFalse(InputValidator.isValidFirstName("Lisa "));

    }

    @Test
    public void testValidEmail() {
        assertTrue(InputValidator.isValidEmail("john.doe@example.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@com"));
        assertFalse(InputValidator.isValidEmail("john.doe@.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@com."));

        //Testen der Randfälle:

        assertFalse(InputValidator.isValidEmail("  "));
        assertFalse(InputValidator.isValidEmail(""));
        assertFalse(InputValidator.isValidEmail(" john.doe@example.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@example.com "));
        */
    }
}
