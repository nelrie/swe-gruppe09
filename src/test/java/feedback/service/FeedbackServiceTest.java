package feedback.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import feedback.domain.Status;
import feedback.validation.InputValidator;
import org.junit.jupiter.api.BeforeEach;
import feedback.domain.Feedback;
import feedback.repository.FeedbackRepository;
import feedback.repository.MockFeedbackRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FeedbackServiceTest {

    private FeedbackRepository feedbackRepository;
    private FeedbackService feedbackService;
    private Feedback feedback;

    // Hinzugefügt nach KI-Review: Initialisiert das FeedbackRepository und FeedbackService vor jedem Test
@BeforeEach
public void setUp(){
    //Mock-FeedbackRepository erstellen
    feedbackRepository  = new MockFeedbackRepository();

    //FeedbackService initialisieren mit FeedbackRepository
    feedbackService = new FeedbackService(feedbackRepository);

    //Erstellt ein Feedback Objekt vor jedem Test
    feedback = feedbackService.erstelleFeedback("Lisa-Marie", "Heufer-Umlauf", "lisa-marie.heufer-umlauf@gmail.com", "Ich liebe die Stadtverwaltung!");
}

    //Test zum Erstellen eines Feedbacks
    @Test
    public void testErstelleFeedback() {

        // Findet das erstellte Feedback im Repository
        Feedback gefundenesFeedback = feedbackRepository.findById(feedback.getFeedbackID());

        //Überprüft, dass Feedback nicht null ist
        assertNotNull(gefundenesFeedback);

        // Überprüft, ob die Eigenschaften des Feedbacks korrekt sind
        assertEquals("Lisa-Marie", gefundenesFeedback.getFirstName());
        assertEquals("Heufer-Umlauf", gefundenesFeedback.getLastName());
        assertEquals("lisa-marie.heufer-umlauf@gmail.com", gefundenesFeedback.getEmail());
        assertEquals("Ich liebe die Stadtverwaltung!", gefundenesFeedback.getMessage());


    }


    @Test
    public void testLoescheFeedback() {

       // Löscht Feedback anhand der ID
        feedbackService.loescheFeedback(feedback.getFeedbackID());

        // Versucht, das gelöschte Feedback in Repository zu finden
        Feedback gefundenesFeedback = feedbackRepository.findById(feedback.getFeedbackID());

        //Überprüft, ob das Feedback null ist, also gelöscht wurde
        assertNull(gefundenesFeedback);

    }

    @Test
    public void testFindeFeedback() {
        Feedback gefundenesFeedback = feedbackService.findeFeedback(feedback.getFeedbackID());
        assertNotNull(gefundenesFeedback);
        assertEquals("Lisa-Marie", gefundenesFeedback.getFirstName());
        assertEquals("Heufer-Umlauf", gefundenesFeedback.getLastName());
        assertEquals("lisa-marie.heufer-umlauf@gmail.com", gefundenesFeedback.getEmail());
        assertEquals("Ich liebe die Stadtverwaltung!", gefundenesFeedback.getMessage());


    }

    @Test
    void validateInput_shouldThrowExceptionForInvalidFirstName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> feedbackService.validateInput("", "Mustermann", "test@example.com", "Feedback")
        );
        assertEquals("Ungültiger Vorname", exception.getMessage());
    }

    @Test
    void validateInput_shouldThrowExceptionForInvalidLastName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> feedbackService.validateInput("Max", "", "test@example.com", "Feedback")
        );
        assertEquals("Ungültiger Nachname", exception.getMessage());
    }

    @Test
    void validateInput_shouldThrowExceptionForInvalidEmail() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> feedbackService.validateInput("Max", "Mustermann", "invalid-email", "Feedback")
        );
        assertEquals("Ungültige E-Mail-Adresse", exception.getMessage());
    }

    @Test
    void validateInput_shouldThrowExceptionForInvalidMessage() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> feedbackService.validateInput("Max", "Mustermann", "test@example.com", "")
        );
        assertEquals("Nachricht darf nicht leer sein", exception.getMessage());
    }

    @Test
    void validateInput_shouldNotThrowExceptionForValidInput() {
        assertDoesNotThrow(() ->
                feedbackService.validateInput("Max", "Mustermann", "test@example.com", "Feedback")
        );
    }

    @Test
    public void testClassInstantiation() {
        // Testet, ob die Klasse erfolgreich instanziiert werden kann
        InputValidator validator = new InputValidator();
        assertNotNull(validator);
    }

    @Test
    public void testFindAllEmptyList() {
        // Arrange
        MockFeedbackRepository repository = new MockFeedbackRepository();

        // Act
        List<Feedback> result = repository.findAll();

        // Assert
        assertNotNull(result); // Die Liste sollte nicht null sein
        assertTrue(result.isEmpty()); // Die Liste sollte leer sein
    }

    @Test
    public void testFindAllWithFeedback() {
        // Arrange
        MockFeedbackRepository repository = new MockFeedbackRepository();
        Feedback feedback1 = new Feedback("1", "John", "Doe", "john.doe@example.com", "Testnachricht 1");
        Feedback feedback2 = new Feedback("2", "Jane", "Smith", "jane.smith@example.com", "Testnachricht 2");
        repository.save(feedback1);
        repository.save(feedback2);

        // Act
        List<Feedback> result = repository.findAll();

        // Assert
        assertNotNull(result); // Die Liste sollte nicht null sein
        assertEquals(2, result.size()); // Es sollten 2 Feedbacks in der Liste sein
        assertTrue(result.contains(feedback1)); // Feedback1 sollte enthalten sein
        assertTrue(result.contains(feedback2)); // Feedback2 sollte enthalten sein
    }

    @Test
    public void testFindeFeedbackThrowsExceptionWhenNotFound() {
        // Arrange: Erstelle ein Mock-FeedbackRepository
        FeedbackRepository mockRepository = mock(FeedbackRepository.class);
        FeedbackService feedbackService = new FeedbackService(mockRepository);

        String feedbackID = "non-existing-id";

        // Simuliere, dass kein Feedback gefunden wird
        when(mockRepository.findById(feedbackID)).thenReturn(null);

        // Act & Assert: Teste, ob die IllegalArgumentException geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> feedbackService.findeFeedback(feedbackID));

        assertEquals("Das Feedback konnte nicht gefunden werden.", thrown.getMessage());
    }

    @Test
    public void testLoescheFeedbackThrowsExceptionWhenNotFound() {
        // Arrange: Erstelle ein Mock-FeedbackRepository
        FeedbackRepository mockRepository = mock(FeedbackRepository.class);
        FeedbackService feedbackService = new FeedbackService(mockRepository);

        // Simuliere, dass kein Feedback mit der gegebenen ID gefunden wird
        String feedbackID = "non-existing-id";
        when(mockRepository.findById(feedbackID)).thenReturn(null);

        // Act & Assert: Teste, ob die IllegalArgumentException geworfen wird
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> feedbackService.loescheFeedback(feedbackID));

        // Überprüfe die Fehlermeldung
        assertEquals("Feedback mit der ID " + feedbackID + " konnte nicht gefunden werden.", thrown.getMessage());
    }

    @Test
    public void testFindeAlleFeedbacksReturnsEmptyList() {
        // Arrange: Erstelle ein Mock-FeedbackRepository und FeedbackService
        FeedbackRepository mockRepository = mock(FeedbackRepository.class);
        FeedbackService feedbackService = new FeedbackService(mockRepository);

        // Simuliere, dass das Repository eine leere Liste zurückgibt
        when(mockRepository.findAll()).thenReturn(List.of());

        // Act: Rufe die Methode auf, die `findAll()` nutzt
        List<Feedback> result = feedbackService.findeAlleFeedbacks();

        // Assert: Überprüfe, ob die zurückgegebene Liste leer ist
        assertTrue(result.isEmpty());  // Überprüfe, ob die Liste leer ist
    }

    @Test
    public void testToString() {
        // Arrange: Erstelle ein Feedback-Objekt
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");

        // Act: Rufe toString() auf
        String result = feedback.toString();

        // Assert: Überprüfe, ob der zurückgegebene String die erwarteten Daten enthält
        assertTrue(result.contains("Feedback von Max Mustermann (max@example.com): Great app!"));
    }

    @Test
    public void testGetAndSetFeedbackID() {
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");
        feedback.setFeedbackID("2");
        assertEquals("2", feedback.getFeedbackID());
    }

    @Test
    public void testGetAndSetFirstName() {
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");
        feedback.setFirstName("John");
        assertEquals("John", feedback.getFirstName());
    }

    @Test
    public void testGetAndSetLastName() {
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");
        feedback.setLastName("Doe");
        assertEquals("Doe", feedback.getLastName());
    }

    @Test
    public void testGetAndSetEmail() {
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");
        feedback.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", feedback.getEmail());
    }

    @Test
    public void testGetAndSetMessage() {
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");
        feedback.setMessage("Good feedback!");
        assertEquals("Good feedback!", feedback.getMessage());
    }

    @Test
    public void testSetFeedbackID() {
        // Arrange: Erstelle ein Feedback-Objekt ohne ID
        Feedback feedback = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");

        // Act: Setze eine neue ID
        feedback.setFeedbackID("123");

        // Assert: Überprüfe, ob die ID korrekt gesetzt wurde
        assertEquals("123", feedback.getFeedbackID());
    }

    @Test
    public void testSetStatus() {
        // Arrange
        Feedback feedback = new Feedback("1", "John", "Doe", "john.doe@example.com", "Message");

        // Act
        feedback.setStatus(Status.COMPLETED); // setStatus wird hier aufgerufen

        // Assert
        assertEquals(Status.COMPLETED, feedback.getStatus());
    }

    @Test
    public void testFindByIdWhenFeedbackExists() {
        // Arrange: Erstelle ein FeedbackRepository und füge ein Feedback hinzu
        MockFeedbackRepository feedbackRepository = new MockFeedbackRepository();
        Feedback feedback1 = new Feedback("1", "Max", "Mustermann", "max@example.com", "Great app!");
        feedbackRepository.save(feedback1); // Füge das Feedback in die Liste hinzu

        // Act: Suche nach dem Feedback mit der ID "1"
        Feedback result = feedbackRepository.findById("1");

        // Assert: Überprüfe, dass das Feedback mit der richtigen ID zurückgegeben wurde
        assertNotNull(result);
        assertEquals("1", result.getFeedbackID());
        assertEquals("Max", result.getFirstName());
        assertEquals("Mustermann", result.getLastName());
    }

    @Test
    void testFindById_NotFound() {
        MockFeedbackRepository repository = new MockFeedbackRepository();
        Feedback feedback = new Feedback("1", "John", "Doe", "john.doe@example.com", "Great service!");
        repository.save(feedback);

        Feedback result = repository.findById("2"); // ID, die nicht existiert

        assertNull(result); // Teste, dass null zurückgegeben wird
    }
}