package status.ui.controller;

import feedback.ui.controller.JavaFXTestBase;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import status.application.service.StatusService;
import status.domain.model.Status;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class StatusPageControllerTest extends JavaFXTestBase {

    @InjectMocks
    private StatusPageController statusPageController;

    @Mock
    private StatusService statusServiceMock;

    private TextField feedbackIDField;
    private Label statusLabel;
    private Button submitButton;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        statusPageController = new StatusPageController(statusServiceMock);
        feedbackIDField = new TextField();
        statusLabel = new Label();
        submitButton = new Button();

        statusPageController.setFeedbackIDField(feedbackIDField);
        statusPageController.setStatusLabel(statusLabel);
        statusPageController.setSubmitButton(submitButton);

        // Aufruf der Initialisierungsmethode, um Standardwerte zu setzen
        statusPageController.initialize();

        assertNotNull(statusPageController, "Controller ist nicht korrekt initialisiert und ist null.");
    }

    @Test
    void testInitialize() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Act: Initialisiere den Controller
                statusPageController.initialize();

                // Assert: Verifiziere, dass das Label den Standardtext hat
                assertEquals("Bitte geben Sie Ihre Feedback-ID ein.", statusLabel.getText());
                // Verifiziere, dass der Submit-Button initialisiert wurde
                assertNotNull(submitButton);
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleSubmitButtonAction_ValidID() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
        try{
        // Arrange: Simulierte Benutzereingabe
        String feedbackID = "123456";
        feedbackIDField.setText(feedbackID);

        // Erwarteter Status
        Status statusMock = Status.RECEIVED;
        when(statusServiceMock.getStatus(feedbackID)).thenReturn(statusMock);

        // Act: handleSubmitButtonAction aufrufen
        statusPageController.handleSubmitButtonAction();

        // Verifiziere, dass das Label den korrekten Text hat
        assertEquals("Der aktuelle Status Ihres Feedbacks ist: " + statusMock.getDescription(), statusLabel.getText());

        // Verifizieren: Mock Service wurde mit richtiger Feedback ID aufgerufen
        verify(statusServiceMock, times(1)).getStatus(feedbackID);

        } finally {
            latch.countDown();
        }
        });
        latch.await(5, TimeUnit.SECONDS);
    }




    @Test
    void testHandleSubmitButtonAction_InvalidID() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Arrange: Simulierte Benutzereingabe
                feedbackIDField.setText("invalid-id");

                // Mock-Service: Werfe eine IllegalArgumentException
                when(statusServiceMock.getStatus("invalid-id")).thenThrow(new IllegalArgumentException("Ungültige Feedback-ID"));

                /// Spy auf den Controller, um die showAlert-Methode zu überwachen
                StatusPageController spyController = spy(statusPageController);
                doAnswer(invocation -> {
                    String message = invocation.getArgument(0);
                    assertEquals("Ungültige Feedback-ID: invalid-id", message);
                    return null;
                }).when(spyController).showAlert(anyString());


                // Act: handleSubmitButtonAction aufrufen
                spyController.handleSubmitButtonAction();

                // Assert: Verifiziere, dass der StatusService aufgerufen wurde
                verify(statusServiceMock, times(1)).getStatus("invalid-id");

                // Verifiziere, dass das Label den korrekten Text hat
                assertEquals("Bitte geben Sie Ihre Feedback-ID ein.", statusLabel.getText());

                // Verifiziere, dass die Fehlermeldung angezeigt wurde
                verify(spyController, times(1)).showAlert("Ungültige Feedback-ID: invalid-id");


            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testHandleSubmitButtonAction_NoStatusFound() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Arrange: Simulierte Benutzereingabe
                feedbackIDField.setText("123456");

                // Mock-Service: Rückgabe von null
                when(statusServiceMock.getStatus("123456")).thenReturn(null);

                // Act: handleSubmitButtonAction aufrufen
                statusPageController.handleSubmitButtonAction();

                // Assert: Verifiziere, dass der StatusService aufgerufen wurde
                verify(statusServiceMock, times(1)).getStatus("123456");

                // Verifiziere, dass das Label den korrekten Text hat
                assertEquals("Kein Status für die angegebene Feedback-ID gefunden.", statusLabel.getText());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testGetStatusFromRepository_ValidID() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Arrange: Simulierte Benutzereingabe
                feedbackIDField.setText("123456");

                // Erwarteter Status
                Status statusMock = Status.RECEIVED;
                when(statusServiceMock.getStatus("123456")).thenReturn(statusMock);

                // Act: getStatusFromRepository aufrufen
                statusPageController.getStatusFromRepository();

                // Assert: Verifiziere, dass der StatusService aufgerufen wurde
                verify(statusServiceMock, times(1)).getStatus("123456");

                // Verifiziere, dass das Label den korrekten Text hat
                assertEquals("Der aktuelle Status Ihres Feedbacks ist: Feedback erhalten", statusLabel.getText());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testGetStatusFromRepository_InvalidID() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Arrange: Simulierte Benutzereingabe
                feedbackIDField.setText("invalid-id");

                // Mock-Service: Werfe eine IllegalArgumentException
                when(statusServiceMock.getStatus("invalid-id")).thenThrow(new IllegalArgumentException("Ungültige Feedback-ID"));

                // Spy auf den Controller, um die showAlert-Methode zu überwachen
                StatusPageController spyController = spy(statusPageController);
                doAnswer(invocation -> {
                    String message = invocation.getArgument(0);
                    assertEquals("Ungültige Feedback-ID: invalid-id", message);
                    return null;
                }).when(spyController).showAlert(anyString());

                // Act: getStatusFromRepository aufrufen
                spyController.getStatusFromRepository();

                // Assert: Verifiziere, dass der StatusService aufgerufen wurde
                verify(statusServiceMock, times(1)).getStatus("invalid-id");

                // Verifiziere, dass das Label den korrekten Text hat
                assertEquals("Bitte geben Sie Ihre Feedback-ID ein.", statusLabel.getText());

                // Verifiziere, dass die Fehlermeldung angezeigt wurde
                verify(spyController, times(1)).showAlert("Ungültige Feedback-ID: invalid-id");
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testGetStatusFromRepository_NoStatusFound() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                // Arrange: Simulierte Benutzereingabe
                feedbackIDField.setText("123456");

                // Mock-Service: Rückgabe von null
                when(statusServiceMock.getStatus("123456")).thenReturn(null);

                // Act: getStatusFromRepository aufrufen
                statusPageController.getStatusFromRepository();

                // Assert: Verifiziere, dass der StatusService aufgerufen wurde
                verify(statusServiceMock, times(1)).getStatus("123456");

                // Verifiziere, dass das Label den korrekten Text hat
                assertEquals("Kein Status für die angegebene Feedback-ID gefunden.", statusLabel.getText());
            } finally {
                latch.countDown();
            }
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @Test
    void testShowAlert() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            statusPageController.showAlert("Test Alert!");
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }
}