package feedback.ui.controller;

import javafx.application.Platform;
import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(MockitoExtension.class)
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
    void testInitialize(){
        Platform.runLater(() -> {

                // Act: Initialisiere den Controller
                startPageController.initialize();

                // Assert: Verifiziere, dass die LineSpacing-Eigenschaft gesetzt wurde
                assertEquals(10, contentText.getLineSpacing());

        });

    }
}