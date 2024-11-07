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
    }
    @Test
    public void testValidLastName() {
        assertTrue(InputValidator.isValidLastName("John Doe"));
        assertTrue(InputValidator.isValidLastName("Lisa-Marie"));
        assertFalse(InputValidator.isValidLastName("John123"));
        assertFalse(InputValidator.isValidLastName(""));
    }

    @Test
    public void testValidEmail() {
        assertTrue(InputValidator.isValidEmail("john.doe@example.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@com"));
        assertFalse(InputValidator.isValidEmail("john.doe@.com"));
        assertFalse(InputValidator.isValidEmail("john.doe@com."));
    }
}