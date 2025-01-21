package feedback.exceptions.validation;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//Klasse, um eine kürzere UUID zu generieren
public class IdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);

    public static String generateShortUuid() {
        UUID uuid = UUID.randomUUID();

        logger.info("ID: {}", uuid);

        return uuid.toString().substring(0, 8);
    }
}
