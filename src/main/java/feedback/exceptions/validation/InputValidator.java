package feedback.exceptions.validation;

import java.util.regex.Pattern;

public class InputValidator {

    //private static final String FIRSTNAME_PATTERN = "^[A-Za-z]+(-[A-Za-z]+)*$";
    private static final String FIRSTNAME_PATTERN = "^(?! )[A-Za-zÄÖÜäöüß]+(?:[- ][A-Za-zÄÖÜäöüß]+)*(?<! )$";
    // Überarbeitete Regular Expression mit dem auch die Randfälle abgedeckt sind
    private static final String LASTNAME_PATTERN = "^(?! )[A-Za-zÄÖÜäöüß]+(?:[- ][A-Za-zÄÖÜäöüß]+)*(?<! )$";
    //private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\-[A-Za-z]{2,}$";
    private static final String EMAIL_PATTERN = "^(?! )[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}(?<! )$";

    public static boolean isValidFirstName(String firstName) {
        return Pattern.matches(FIRSTNAME_PATTERN, firstName);
    }
    public static boolean isValidLastName(String lastName) {
        return Pattern.matches(LASTNAME_PATTERN, lastName);
    }
    public static boolean isValidEmail(String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }
    public static boolean isValidMessage(String message) { return message != null && !message.trim().isEmpty(); }
}