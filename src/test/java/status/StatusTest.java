package status;

import feedback.domain.Status;
import feedback.service.StatusService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {

    private StatusService statusService;

    @BeforeEach
    public void setUp() {
        // Erstellen eines neuen StatusService für jeden Testfall
        statusService = new StatusService("Max Mustermann");
    }

    @Test
    public void testInitialStatus() {
        // Testet, ob der Status auf "RECEIVED" gesetzt ist
        assertEquals(Status.RECEIVED, statusService.getStatus(),
                "Der Status sollte 'Feedback erhalten' sein.");
    }

    @Test
    public void testSetStatusInProgress() {
        // Setzt den Status auf "IN_PROGRESS" und überprüft, ob er korrekt gesetzt wurde
        statusService.setStatus(Status.IN_PROGRESS);
        assertEquals(Status.IN_PROGRESS, statusService.getStatus(),
                "Der Status sollte 'Feedback wird bearbeitet' sein.");
    }

    @Test
    public void testSetStatusCompleted() {
        // Setzt den Status auf "COMPLETED" und überprüft, ob er korrekt gesetzt wurde
        statusService.setStatus(Status.COMPLETED);
        assertEquals(Status.COMPLETED, statusService.getStatus(),
                "Der Status sollte 'Feedback abgeschlossen' sein.");
    }

    @Test
    public void testSendStatusUpdate() {
        // Fängt die Systemausgabe ab, um zu überprüfen, ob die Nachricht korrekt gesendet wird
        statusService.setStatus(Status.COMPLETED);

        // Leite die Ausgabe auf einen Byte-Stream um
        java.io.ByteArrayOutputStream outContent = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(outContent));

        // Rufe die sendStatusUpdate-Methode auf
        statusService.sendStatusUpdate();

        // Überprüfe die Ausgabe
        String expectedOutput = "Der Benutzer 'Max Mustermann' hat den Feedback-Status verschickt: Feedback abgeschlossen";
        assertTrue(outContent.toString().trim().contains(expectedOutput),
                "Die sendStatusUpdate-Methode sollte die richtige Statusnachricht ausgeben.");

        // Setze System.out zurück
        System.setOut(System.out);
    }
}