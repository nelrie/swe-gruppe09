package feedback.exceptions.validation;

import java.util.regex.Pattern;

public class InputValidator {

    private static final String NAME_PATTERN = "^(?! )[A-Za-zÄÖÜäöüß]+(?:[- ][A-Za-zÄÖÜäöüß]+)*(?<! )$";

    private static final String EMAIL_PATTERN = "^(?! )[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(?<! )$";

    public static boolean isValidName(String name) {
        return Pattern.matches(NAME_PATTERN, name);
    }

    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }
    public static boolean isValidMessage(String message) {
        return message != null && !message.trim().isEmpty(); }
}