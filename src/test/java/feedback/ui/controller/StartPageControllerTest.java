package feedback.ui.controller;

import javafx.application.Platform;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StartPageControllerTest extends JavaFXTestBase {

    @InjectMocks
    private StartPageController startPageController;

    @Mock
    private ApplicationContext contextMock;

    private TextFlow contentText;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        contentText = new TextFlow();
        startPageController.setContentText(contentText);
    }

    @Test
    void testInitialize() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Act: Initialisiere den Controller
                startPageController.initialize();

                // Assert: Verifiziere, dass die LineSpacing-Eigenschaft gesetzt wurde
                assertEquals(10, contentText.getLineSpacing());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}