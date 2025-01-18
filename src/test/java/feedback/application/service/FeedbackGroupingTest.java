package feedback.application.service;

import feedback.domain.model.Feedback;
import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import feedback.infrastructure.repository.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import status.domain.model.Status;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FeedbackGroupingTest{

    private static final Logger logger = LoggerFactory.getLogger(FeedbackGroupingTest.class);

    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;

    @BeforeEach
    void setUp() {
        // Mock-Repository erstellen
        feedbackRepository = mock(FeedbackRepository.class);
        feedbackService = new FeedbackService(feedbackRepository, null, null); // StatusService und EventPublisher sind hier nicht relevant
    }

    @Test
    void testStatistikFeedbackProStatus() {
        // Vorbereitung: Erstellen der Feedback-Objekte mit verschiedenen Status-Werten
        Feedback feedback1 = new Feedback("1", new FullName("Max", "Mustermann"), new Email("max@test.com"), new Message("Testnachricht 1"));
        feedback1.setStatus(Status.RECEIVED); // Setzt den Status

        Feedback feedback2 = new Feedback("2", new FullName("Anna", "Musterfrau"), new Email("anna@test.com"), new Message("Testnachricht 2"));
        feedback2.setStatus(Status.IN_PROGRESS);

        Feedback feedback3 = new Feedback("3", new FullName("John", "Doe"), new Email("john@test.com"), new Message("Testnachricht 3"));
        feedback3.setStatus(Status.IN_PROGRESS);

        Feedback feedback4 = new Feedback("4", new FullName("Clara", "Schmidt"), new Email("clara@test.com"), new Message("Testnachricht 4"));
        feedback4.setStatus(Status.COMPLETED);

        Feedback feedback5 = new Feedback("5", new FullName("Emily", "Clark"), new Email("emily@test.com"), new Message("Testnachricht 5"));
        feedback5.setStatus(Status.COMPLETED);

        Feedback feedback6 = new Feedback("6", new FullName("Chris", "Evans"), new Email("chris@test.com"), new Message("Testnachricht 6"));
        feedback6.setStatus(Status.IN_PROGRESS);

        // Mocking: Verhalten des Repositories definieren
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2, feedback3, feedback4, feedback5, feedback6));

        // Ausführen der Methode
        Map<Status, Long> statistik = feedbackService.statistikFeedbackProStatus();

        // Assertions: Überprüfen, ob die Statistik korrekt ist
        assertNotNull(statistik);
        assertEquals(1, statistik.get(Status.RECEIVED));    // 1 Feedbacks mit Status RECEIVED
        assertEquals(3, statistik.get(Status.IN_PROGRESS)); // 3 Feedbacks mit Status IN_PROGRESS
        assertEquals(2, statistik.get(Status.COMPLETED));   // 2 Feedbacks mit Status COMPLETED

        // Konsolenausgabe zur Validierung
        logger.info("Statistik der Feedback-Status:");
        statistik.forEach((status, count) -> {
            logger.info("{}: {}", status, count); // Verwenden des SLF4J-Formats für Logging
        });
    }

    @Test
    void testGetAllEmails() {
        // Vorbereitung: Erstellen von sechs Feedback-Objekten mit unterschiedlichen (und teils gleichen) E-Mail-Adressen
        Feedback feedback1 = new Feedback("1", new FullName("Max", "Mustermann"), new Email("zebra@test.com"), new Message("Nachricht 1"));
        Feedback feedback2 = new Feedback("2", new FullName("Anna", "Musterfrau"), new Email("apple@test.com"), new Message("Nachricht 2"));
        Feedback feedback3 = new Feedback("3", new FullName("John", "Doe"), new Email("banana@test.com"), new Message("Nachricht 3"));
        Feedback feedback4 = new Feedback("4", new FullName("Clara", "Schmidt"), new Email("apple@test.com"), new Message("Nachricht 4")); // Duplikat von apple@test.com
        Feedback feedback5 = new Feedback("5", new FullName("Emily", "Clark"), new Email("cherry@test.com"), new Message("Nachricht 5"));
        Feedback feedback6 = new Feedback("6", new FullName("Chris", "Evans"), new Email("banana@test.com"), new Message("Nachricht 6"));   // Duplikat von banana@test.com

        // Mocking: Verhalten des Repositories definieren
        Mockito.when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2, feedback3, feedback4, feedback5, feedback6));

        // Ausführung der Methode
        List<String> sortedEmails = feedbackService.getAllEmails();

        // Assertions: Überprüfen, ob die E-Mail-Adressen korrekt sortiert und ohne Duplikate sind
        assertNotNull(sortedEmails);
        assertEquals(4, sortedEmails.size()); // Es sollten 4 eindeutige E-Mails geben (keine Duplikate)
        assertEquals(List.of("apple@test.com", "banana@test.com", "cherry@test.com", "zebra@test.com"), sortedEmails); // Alphabetische Reihenfolge prüfen

        // Konsolenausgabe zur Prüfung (optional)
        logger.info("E-Mail-Adressen der Nutzer:");
        sortedEmails.forEach(email -> logger.info(email));

//

    }


}