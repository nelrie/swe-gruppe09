package feedback.validation;

import java.util.regex.Pattern;

public class InputValidator {

    private static final String FIRSTNAME_PATTERN = "^[A-Za-z]+(-[A-Za-z]+)*$";
    private static final String LASTNAME_PATTERN = "^[A-Za-z]+(-[A-Za-z]+)*$"
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public static boolean isValidName(String firstName) {
        return Pattern.matches(NAME_PATTERN, firstName);
    }
    public static boolean isValidName(String lastName) {
        return Pattern.matches(NACHNAME_PATTERN, lastName);
    }
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }
}