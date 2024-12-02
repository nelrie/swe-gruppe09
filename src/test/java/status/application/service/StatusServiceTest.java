package status.application.service;

import status.domain.model.Status;
import status.infrastructure.repository.MockStatusRepository;
import status.infrastructure.repository.StatusRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatusServiceTest {

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
    public void testInitialStatus() {
        // Testet, ob der Status auf "RECEIVED" gesetzt ist
        String feedbackID = "test-feedback-id";
        statusService.setInitialStatus(feedbackID);
        assertEquals(Status.RECEIVED, statusService.getStatus(feedbackID),
                "Der Status sollte 'Feedback erhalten' sein.");
    }

    @Test
    public void testSetStatusInProgress() {
        // Setzt den Status auf "IN_PROGRESS" und überprüft, ob er korrekt gesetzt wurde
        String feedbackID = "test-feedback-id";
        statusService.setStatus(feedbackID, Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, statusService.getStatus(feedbackID),
                "Der Status sollte 'Feedback wird bearbeitet' sein.");
    }

    @Test
    public void testSetStatusCompleted() {
        // Setzt den Status auf "COMPLETED" und überprüft, ob er korrekt gesetzt wurde
        String feedbackID = "test-feedback-id";
        statusService.setStatus(feedbackID, Status.COMPLETED);
        assertEquals(Status.COMPLETED, statusService.getStatus(feedbackID),
                "Der Status sollte 'Feedback abgeschlossen' sein.");
    }

    @Test
    public void testSendStatusUpdate() {
        // Fängt die Systemausgabe ab, um zu überprüfen, ob die Nachricht korrekt gesendet wird
        String feedbackID = "test-feedback-id";
        statusService.setStatus(feedbackID, Status.COMPLETED);

        // Leite die Ausgabe auf einen Byte-Stream um
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Rufe die sendStatusUpdate-Methode auf
        statusService.sendStatusUpdate(feedbackID);

        // Überprüfe die Ausgabe
        String expectedOutput = "Der Benutzer 'Max Mustermann' hat den Feedback-Status verschickt: Feedback abgeschlossen";
        assertTrue(outContent.toString().trim().contains(expectedOutput),
                "Die sendStatusUpdate-Methode sollte die richtige Statusnachricht ausgeben.");

        // Setze System.out zurück
        System.setOut(System.out);
    }
}