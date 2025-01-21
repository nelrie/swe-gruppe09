package feedback.ui.controller;


import feedback.application.commands.CreateFeedbackCommand;
import feedback.application.service.FeedbackService;
import feedback.domain.model.Feedback;
import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackFormControllerTest extends JavaFXTestBase {

    @InjectMocks
    private FeedbackFormController feedbackFormController; // Automatische Injektion der gemockten Beans

    @Mock
    private FeedbackService feedbackServiceMock;


    // Echte Instanzen für JavaFX-Komponenten
    private TextField firstNameField;
    private TextField lastNameField;
    private TextField emailField;
    private TextArea messageArea;


    @BeforeEach
    void setUp() {
        // Initialisierung der Mockito-Mocks
        MockitoAnnotations.openMocks(this);

        // Erstellt echte Instanzen für alle JavaFX-Komponenten
        firstNameField = new TextField();
        lastNameField = new TextField();
        emailField = new TextField();
        messageArea = new TextArea();

        // Setzt die Textfelder im Controller
        feedbackFormController.setFirstNameField(firstNameField);
        feedbackFormController.setLastNameField(lastNameField);
        feedbackFormController.setEmailField(emailField);
        feedbackFormController.setMessageArea(messageArea);
    }
    @Test
    void testInitialize() {
        Platform.runLater(() -> {
                // Act: Initialisiere den Controller
                feedbackFormController.initialize();

                // Assert: Verifiziere, dass die Felder geleert wurden
                assertEquals("", firstNameField.getText());
                assertEquals("", lastNameField.getText());
                assertEquals("", emailField.getText());
        });
    }

    @Test
    void testHandleSubmitFeedback_ValidInput(){
        Platform.runLater(() -> {
                setValidInputFields();

                Feedback feedbackMock = createMockFeedback();

                // Mock-Service: Rückgabe des erwarteten Feedback-Objekts
                when(feedbackServiceMock.erstelleFeedback(any(CreateFeedbackCommand.class))).thenReturn(feedbackMock);

                // Act: handleSubmitFeedback aufrufen
                feedbackFormController.handleSubmitFeedback();

                verifyFeedbackServiceCalledOnce();
                verifyFieldsCleared();
                verifySuccessAlertShown(feedbackMock.getFeedbackID());
        });

    }

    @Test
    void testHandleSubmitFeedback_InvalidInput(){
        Platform.runLater(() -> {
                    setInvalidInputFields();

                    feedbackFormController.handleSubmitFeedback();

                    verify(feedbackServiceMock, never()).erstelleFeedback(any(CreateFeedbackCommand.class));
                    verifyFieldsNotCleared();
                    verifyWarningAlertShown();
        });

    }


    @Test
    void testHandleSubmitFeedback_Exception() {
        Platform.runLater(() -> {
                setValidInputFields();

                when(feedbackServiceMock.erstelleFeedback(any(CreateFeedbackCommand.class))).thenThrow(new RuntimeException("Test Exception"));
                feedbackFormController.handleSubmitFeedback();

                verifyFeedbackServiceCalledOnce();
                verifyFieldsNotCleared();
                verifyErrorAlertShown("Test Exception");
        });
    }

    @Test
    void testShowAlert()  {
        Platform.runLater(() -> {
            feedbackFormController.showAlert("Test Alert!", Alert.AlertType.INFORMATION);
        });
    }



    /*
    Hilfsmethoden um Code Duplikationen zu vermeiden und den Code lesbarer zu machen
    */

    @Test
    void testClearFieldsDirectly() {
        setValidInputFields();
        feedbackFormController.clearFields();
        verifyFieldsCleared();
    }

    private void setValidInputFields() {
        firstNameField.setText("Max");
        lastNameField.setText("Mustermann");
        emailField.setText("max.mustermann@example.com");
        messageArea.setText("Dies ist eine Testnachricht.");
    }

    private void setInvalidInputFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("invalid-email-format");
        messageArea.setText("");
    }

    private Feedback createMockFeedback() {
        return new Feedback(
                "123456",
                new FullName("Max", "Mustermann"),
                new Email("max.mustermann@example.com"),
                new Message("Dies ist eine Testnachricht.")
        );
    }

    private void verifyFeedbackServiceCalledOnce() {
        verify(feedbackServiceMock, times(1)).erstelleFeedback(any(CreateFeedbackCommand.class));
    }

    private void verifyFieldsCleared() {
        assertEquals("", firstNameField.getText());
        assertEquals("", lastNameField.getText());
        assertEquals("", emailField.getText());
        assertEquals("", messageArea.getText());
    }

    private void verifyFieldsNotCleared() {
        assertEquals("Max", firstNameField.getText());
        assertEquals("Mustermann", lastNameField.getText());
        assertEquals("max.mustermann@example.com", emailField.getText());
        assertEquals("Dies ist eine Testnachricht.", messageArea.getText());
    }

    private void verifySuccessAlertShown(String feedbackID) {
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Alert.AlertType> alertTypeCaptor = ArgumentCaptor.forClass(Alert.AlertType.class);
        verify(feedbackFormController, times(1)).showAlert(messageCaptor.capture(), alertTypeCaptor.capture());
        assertEquals("Feedback erfolgreich gesendet! Ihre Feedback-ID ist " + feedbackID + ". Bitte notieren Sie diese, um bei Bedarf den Status Ihres Feedbacks abzurufen.", messageCaptor.getValue());
        assertEquals(Alert.AlertType.INFORMATION, alertTypeCaptor.getValue());
    }

    private void verifyWarningAlertShown() {
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Alert.AlertType> alertTypeCaptor = ArgumentCaptor.forClass(Alert.AlertType.class);
        verify(feedbackFormController, times(1)).showAlert(messageCaptor.capture(), alertTypeCaptor.capture());
        assertEquals("Die Felder dürfen nicht leer sein oder ungültige Eingaben enthalten.", messageCaptor.getValue());
        assertEquals(Alert.AlertType.WARNING, alertTypeCaptor.getValue());
    }

    private void verifyErrorAlertShown(String errorMessage) {
        ArgumentCaptor<String> messageCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Alert.AlertType> alertTypeCaptor = ArgumentCaptor.forClass(Alert.AlertType.class);
        verify(feedbackFormController, times(1)).showAlert(messageCaptor.capture(), alertTypeCaptor.capture());
        assertEquals("Beim Senden des Feedbacks ist ein Fehler aufgetreten " + errorMessage, messageCaptor.getValue());
        assertEquals(Alert.AlertType.ERROR, alertTypeCaptor.getValue());
    }


}
