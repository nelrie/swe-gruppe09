package feedback.ui.controller;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

// Testklasse um sicherzustellen, dass das JavaFX-Toolkit einmal initialisiert wird

public abstract class JavaFXTestBase {
    private static boolean initialized = false;

    @BeforeAll
    static void initializeJavaFX() throws Exception {
        if (!initialized) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> latch.countDown());
            if (!latch.await(10, TimeUnit.SECONDS)) {
                throw new IllegalStateException("JavaFX Application Thread konnte nicht gestartet werden.");
            }
            initialized = true;
        }
    }
}