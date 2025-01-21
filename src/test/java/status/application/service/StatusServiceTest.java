package status.application.service;

import status.domain.model.Status;
import status.infrastructure.repository.MockStatusRepository;
import status.infrastructure.repository.StatusRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StatusServiceTest {

    private StatusService statusService;
    private StatusRepository statusRepository;

    @BeforeEach
    public void setUp() {
        // Erstellen eines Mock-StatusRepository
        statusRepository = new MockStatusRepository();

        // Initialisieren des StatusService mit dem Mock-StatusRepository
        statusService = new StatusService(statusRepository);
    }

    @Test
    void testInitialStatus() {
        // Testet, ob der Status auf "RECEIVED" gesetzt ist
        String feedbackID = "test-feedback-id";
        statusService.setInitialStatus(feedbackID);
        assertEquals(Status.RECEIVED, statusService.getStatus(feedbackID),
                "Der Status sollte 'Feedback erhalten' sein.");
    }

    @Test
    void testSetStatusInProgress() {
        // Setzt den Status auf "IN_PROGRESS" und überprüft, ob er korrekt gesetzt wurde
        String feedbackID = "test-feedback-id";
        statusService.setStatus(feedbackID, Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, statusService.getStatus(feedbackID),
                "Der Status sollte 'Feedback wird bearbeitet' sein.");
    }

    @Test
    void testSetStatusCompleted() {
        // Setzt den Status auf "COMPLETED" und überprüft, ob er korrekt gesetzt wurde
        String feedbackID = "test-feedback-id";
        statusService.setStatus(feedbackID, Status.COMPLETED);
        assertEquals(Status.COMPLETED, statusService.getStatus(feedbackID),
                "Der Status sollte 'Feedback abgeschlossen' sein.");
    }

    @Test
    void testSendStatusUpdate() {
        // Fängt die Systemausgabe ab, um zu überprüfen, ob die Nachricht korrekt gesendet wird
        String feedbackID = "test-feedback-id";
        statusService.setStatus(feedbackID, Status.COMPLETED);

        // Leite die Ausgabe auf einen Byte-Stream um
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Rufe die sendStatusUpdate-Methode auf
        statusService.sendStatusUpdate(feedbackID);

        // Überprüfe die Ausgabe
        String expectedOutput = "Der Feedback-Status für ID 'test-feedback-id' wurde verschickt: Feedback abgeschlossen";
        assertTrue(outContent.toString().trim().contains(expectedOutput),
                "Die sendStatusUpdate-Methode sollte die richtige Statusnachricht ausgeben.");

        // Setze System.out zurück
        System.setOut(System.out);
    }

    // Auslösen einer IllegalArgumentException, wenn der Status null ist
    @Test
    void testInvalidStatusChange() {
        String feedbackID = "test-feedback-id";
        assertThrows(IllegalArgumentException.class, () -> {
            statusService.setStatus(feedbackID, null);
            }, "Es sollte eine IllegalArgumentException ausgelöst werden, wenn der Status null ist.");
    }

    // Überprüfen, ob der Status null ist, wenn die Feedback-ID nicht existiert
    @Test
    void testGetNonExistentStatus() {
        String feedbackID = "non-existent-feedback-id";
        assertNull(statusService.getStatus(feedbackID), "Der Status sollte null sein, wenn die Feedback-ID nicht existiert.");
    }

    // Überprüfen, ob eine IllegalArgumentException ausgelöst wird, wenn die Feedback-ID ungültig ist
    @Test
    void testSetStatusWithInvalidFeedbackID() {
        String invalidFeedbackID = "invalid-feedback-id";
        assertThrows(IllegalArgumentException.class, () -> {
            statusService.setStatus(invalidFeedbackID, Status.IN_PROGRESS);
            }, "Es sollte eine IllegalArgumentException ausgelöst werden, wenn die Feedback-ID ungültig ist.");
    }


}
