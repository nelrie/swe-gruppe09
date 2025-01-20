package feedback.ui.controller;

import feedback.infrastructure.FeedbackController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SharedUIControllerTest extends JavaFXTestBase{

    @Mock
    private ApplicationContext context;
    @InjectMocks
    private SharedUIController sharedUIController;

    // Echte Instanzen für JavaFX-Komponenten
    private ToggleButton startButton;
    private ToggleButton feedbackButton;
    private ToggleButton statusButton;



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Initialisiere die ToggleButtons
        startButton = new ToggleButton();
        feedbackButton = new ToggleButton();
        statusButton = new ToggleButton();

        // Setze die ToggleButtons im Controller
        sharedUIController.setStartButton(startButton);
        sharedUIController.setFeedbackButton(feedbackButton);
        sharedUIController.setStatusButton(statusButton);
    }

    @Test
    void testOpenStartPage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                Node source = mock(Node.class);
                Scene scene = mock(Scene.class);
                Stage stage = mock(Stage.class);

                when(event.getSource()).thenReturn(source);
                when(source.getScene()).thenReturn(scene);
                when(scene.getWindow()).thenReturn(stage);

                sharedUIController.openStartPage(event);

                assertTrue(startButton.isSelected());
                assertFalse(feedbackButton.isSelected());
                assertFalse(statusButton.isSelected());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testOpenFeedbackForm() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                Node source = mock(Node.class);
                Scene scene = mock(Scene.class);
                Stage stage = mock(Stage.class);

                when(event.getSource()).thenReturn(source);
                when(source.getScene()).thenReturn(scene);
                when(scene.getWindow()).thenReturn(stage);

                sharedUIController.openFeedbackForm(event);

                assertFalse(startButton.isSelected());
                assertTrue(feedbackButton.isSelected());
                assertFalse(statusButton.isSelected());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testOpenStatusPage() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                Node source = mock(Node.class);
                Scene scene = mock(Scene.class);
                Stage stage = mock(Stage.class);

                when(event.getSource()).thenReturn(source);
                when(source.getScene()).thenReturn(scene);
                when(scene.getWindow()).thenReturn(stage);

                sharedUIController.openStatusPage(event);

                assertFalse(startButton.isSelected());
                assertFalse(feedbackButton.isSelected());
                assertTrue(statusButton.isSelected());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testLoadPage_Success() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                Node source = mock(Node.class);
                Scene scene = mock(Scene.class);
                Stage stage = mock(Stage.class);

                when(event.getSource()).thenReturn(source);
                when(source.getScene()).thenReturn(scene);
                when(scene.getWindow()).thenReturn(stage);

                sharedUIController.loadPage(event, "/fxml/valid-page.fxml", "Valid Page");

                // Überprüfe, ob die Szene korrekt gesetzt wurde
                assertEquals("Valid Page", stage.getTitle());
                assertNotNull(stage.getScene());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testLoadPage_InvalidEventSource() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                when(event.getSource()).thenReturn(new Object()); // Ungültige Quelle

                sharedUIController.loadPage(event, "/fxml/invalid-page.fxml", "Invalid Page");

                // Überprüfe, ob der Logger den Fehler protokolliert hat
                if (verify(SharedUIController.getLogger(), times(1)).isErrorEnabled()) {
                    verify(SharedUIController.getLogger(), times(1)).error(anyString(), anyString(), any(IllegalStateException.class));
                }
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testLoadPage_IOException() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                Node source = mock(Node.class);
                Scene scene = mock(Scene.class);
                Stage stage = mock(Stage.class);

                when(event.getSource()).thenReturn(source);
                when(source.getScene()).thenReturn(scene);
                when(scene.getWindow()).thenReturn(stage);

                // Simuliere eine IOException
                doThrow(new IOException("Test IOException")).when(context).getBean(any(Class.class));

                sharedUIController.loadPage(event, "/fxml/invalid-page.fxml", "Invalid Page");

                // Überprüfe, ob der Logger den Fehler protokolliert hat
                verify(SharedUIController.getLogger(), times(1)).error(anyString(), anyString(), any(IOException.class));

            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testLoadPage_InvalidFXML() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                ActionEvent event = mock(ActionEvent.class);
                Node source = mock(Node.class);
                Scene scene = mock(Scene.class);
                Stage stage = mock(Stage.class);

                when(event.getSource()).thenReturn(source);
                when(source.getScene()).thenReturn(scene);
                when(scene.getWindow()).thenReturn(stage);

                sharedUIController.loadPage(event, "/fxml/nonexistent-page.fxml", "Nonexistent Page");

                // Überprüfe, ob der Logger den Fehler protokolliert hat
                if (verify(SharedUIController.getLogger(), times(1)).isErrorEnabled()) {
                    verify(SharedUIController.getLogger(), times(1)).error(anyString(), anyString(), any(IOException.class));
                }
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}


