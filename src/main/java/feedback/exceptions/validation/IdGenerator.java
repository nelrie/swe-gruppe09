package feedback.exceptions.validation;

import java.util.UUID;

//Klasse, um eine kürzere UUID zu generieren
public class IdGenerator {
    public static String generateShortUuid() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }
}
