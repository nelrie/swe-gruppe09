package feedback.exceptions.validation;

import java.util.UUID;

//Klasse, um eine kürzere UUID zu generieren
public class IdGenerator {
    public static String generateShortUuid() {
        UUID uuid = UUID.randomUUID();
        System.out.println("ID: " + uuid);
        return uuid.toString().substring(0, 8);
        //String uuid = "12345678";
        //System.out.println("ID: " + uuid);
        //return uuid;
    }
}
