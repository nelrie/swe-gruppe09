package feedback.service;

import org.junit.jupiter.api.Test;

import feedback.repository.MockFeedbackRepository;
import feedback.domain.Feedback;
import feedback.domain.Status;
import feedback.service.FeedbackService;

import java.util.Map;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class FeedbackGroupingTest {


    @Test
    void testStatistikFeedbackProStatus() {
        // Mock-Repository vorbereiten
        MockFeedbackRepository repository = new MockFeedbackRepository();

        // Beispiel-Feedbacks hinzufügen
        Feedback feedback1 = new Feedback("1", "Max", "Mustermann", "max@test.com", "Testnachricht", Status.RECEIVED);
        Feedback feedback2 = new Feedback("2", "Anna", "Musterfrau", "anna@test.com", "Testnachricht 2", Status.IN_PROGRESS);
        Feedback feedback3 = new Feedback("3", "John", "Doe", "john@test.com", "Testnachricht 3", Status.RECEIVED);
        Feedback feedback4 = new Feedback("4", "Clara", "Schmidt", "clara@test.com", "Testnachricht 4", Status.COMPLETED);

        repository.save(feedback1);
        repository.save(feedback2);
        repository.save(feedback3);
        repository.save(feedback4);

        // FeedbackService mit dem Mock-Repository verwenden
        FeedbackService service = new FeedbackService(repository);

        // Statistik abrufen
        Map<Status, Long> statistik = service.statistikFeedbackProStatus();

        // Assertions
        assertNotNull(statistik);
        assertEquals(2, statistik.get(Status.RECEIVED));    // 2 Feedbacks mit Status RECEIVED
        assertEquals(1, statistik.get(Status.IN_PROGRESS)); // 1 Feedback mit Status IN_PROGRESS
        assertEquals(1, statistik.get(Status.COMPLETED));   // 1 Feedback mit Status COMPLETED
    }

    @Test
    void testGetAllEmails() {

        // Logger initialisieren
        Logger logger = LoggerFactory.getLogger(FeedbackGroupingTest.class);

        // Mock-Repository vorbereiten
        MockFeedbackRepository repository = new MockFeedbackRepository();

        // Beispiel-Feedbacks hinzufügen
        Feedback feedback1 = new Feedback("1", "Max", "Mustermann", "max@test.com", "Testnachricht", Status.RECEIVED);
        Feedback feedback2 = new Feedback("2", "Anna", "Musterfrau", "anna@test.com", "Testnachricht 2", Status.IN_PROGRESS);
        Feedback feedback3 = new Feedback("3", "John", "Doe", "max@test.com", "Testnachricht 3", Status.COMPLETED); // Gleiche E-Mail wie bei feedback1
        Feedback feedback4 = new Feedback("4", "Clara", "Schmidt", "clara@test.com", "Testnachricht 4", Status.RECEIVED);
        Feedback feedback5 = new Feedback("5", "Daniel", "Klein", "daniel@test.org", "Testnachricht 5", Status.IN_PROGRESS);
        Feedback feedback6 = new Feedback("6", "Lisa", "Müller", "lisa123@test.com", "Testnachricht 6", Status.COMPLETED);

        repository.save(feedback1);
        repository.save(feedback2);
        repository.save(feedback3);
        repository.save(feedback4);
        repository.save(feedback5);
        repository.save(feedback6);

        // FeedbackService
        FeedbackService feedbackService = new FeedbackService(repository);

        // Methode testen
        List<String> emails = feedbackService.getAllEmails();

        // Assertions
        assertNotNull(emails); // Die Liste der E-Mails darf nicht null sein
        assertEquals(5, emails.size()); // Es sollten 6 eindeutige E-Mail-Adressen sein
        // Prüfen, ob die erwarteten E-Mail-Adressen in der Liste enthalten sind
        assertTrue(emails.contains("max@test.com"));
        assertTrue(emails.contains("anna@test.com"));
        assertTrue(emails.contains("clara@test.com"));
        assertTrue(emails.contains("daniel@test.org"));
        assertTrue(emails.contains("lisa123@test.com"));

        assertEquals(List.of(
                "anna@test.com",
                "clara@test.com",
                "daniel@test.org",
                "lisa123@test.com",
                "max@test.com"
        ), emails); // Überprüfung auf die exakte alphabetische Reihenfolge

        // Konsolenausgabe simulieren
        logger.info("E-Mail-Adressen der Nutzer:");
        emails.forEach(email -> {logger.info(email);
        });
    }

}