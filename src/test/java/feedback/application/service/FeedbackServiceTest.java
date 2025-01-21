package feedback.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import feedback.application.commands.CreateFeedbackCommand;
import feedback.domain.events.FeedbackAngelegtEvent;
import feedback.domain.valueobjects.Email;
import feedback.domain.valueobjects.FullName;
import feedback.domain.valueobjects.Message;
import feedback.exceptions.validation.IdGenerator;
import org.junit.jupiter.api.BeforeEach;
import feedback.domain.model.Feedback;
import feedback.infrastructure.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import status.application.service.StatusService;
import status.domain.model.Status;
import status.infrastructure.repository.StatusRepository;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
    class FeedbackServiceTest {

@Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private StatusRepository statusRepository;

    @Mock
    private StatusService statusService;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private FeedbackService feedbackService;

    private Feedback feedback;
    private FullName fullName;
    private Email email;
    private Message message;
    private CreateFeedbackCommand feedbackCommand;

    // Hinzugefügt nach KI-Review: Initialisiert das FeedbackRepository und FeedbackService vor jedem Test
@BeforeEach
public void setUp() {

    MockitoAnnotations.openMocks(this);

    // Verwende Mockito-Mocks
    feedbackRepository = mock(FeedbackRepository.class);
    statusRepository = mock(StatusRepository.class);
    eventPublisher = mock(ApplicationEventPublisher.class);

    // Initialisiere den StatusService mit dem gemockten StatusRepository
    statusService = new StatusService(statusRepository);

    // Initialisiere den FeedbackService mit den gemockten Abhängigkeiten
    feedbackService = new FeedbackService(feedbackRepository, statusService, eventPublisher);

    // Erstellen der Value Objects
    fullName = new FullName("Lisa-Marie", "Heufer-Umlauf");
    email = new Email("lisa-marie.heufer-umlauf@gmail.com");
    message = new Message("Dies ist das Feedback von Lisa-Marie.");

    // Erstellen des Commands zum Feedback Abschicken
    feedbackCommand = new CreateFeedbackCommand(fullName, email, message);
}
    //Test zum Erstellen eines Feedbacks
    @Test
    void testErstelleFeedback() {
        // Mocken der statischen Methode IdGenerator.generateShortUuid()
        try (MockedStatic<IdGenerator> mockedIdGenerator = mockStatic(IdGenerator.class)) {
            mockedIdGenerator.when(IdGenerator::generateShortUuid).thenReturn("12345");
            // Erstellen des Feedback-Objekts
            feedback = new Feedback("12345", fullName, email, message);

            // Stubben der save-Methode
            doAnswer(invocation -> {
                Feedback savedFeedback = invocation.getArgument(0);
                savedFeedback.setFeedbackID("12345");
                return null;
            }).when(feedbackRepository).save(any(Feedback.class));

            // Stubben der findById-Methode
            when(feedbackRepository.findById("12345")).thenReturn(feedback);

            // Aufruf der erstelleFeedback-Methode
            Feedback erstelltesFeedback = feedbackService.erstelleFeedback(feedbackCommand);

            // Überprüfen, ob das Feedback korrekt erstellt wurde
            assertNotNull(erstelltesFeedback);
            assertEquals(fullName, erstelltesFeedback.getFullName());
            assertEquals(email, erstelltesFeedback.getEmail());
            assertEquals(message, erstelltesFeedback.getMessage());

            // Überprüfen, ob das Feedback im Repository gefunden wurde
            Feedback gefundenesFeedback = feedbackRepository.findById(erstelltesFeedback.getFeedbackID());
            assertNotNull(gefundenesFeedback);
            assertEquals(fullName, gefundenesFeedback.getFullName());
            assertEquals(email, gefundenesFeedback.getEmail());
            assertEquals(message, gefundenesFeedback.getMessage());

            // Überprüfung, ob das Event veröffentlicht wurde
            ArgumentCaptor<FeedbackAngelegtEvent> eventCaptor = ArgumentCaptor.forClass(FeedbackAngelegtEvent.class);
            verify(eventPublisher).publishEvent(eventCaptor.capture());

            FeedbackAngelegtEvent publishedEvent = eventCaptor.getValue();
            assertEquals(erstelltesFeedback.getFeedbackID(), publishedEvent.getFeedbackID());
            assertEquals(fullName, publishedEvent.getFullName());
            assertEquals(email, publishedEvent.getEmail());
            assertEquals(message, publishedEvent.getMessage());

            // Überprüfen, ob die save-Methode aufgerufen wurde
            verify(feedbackRepository).save(any(Feedback.class));
        }
    }

    @Test
    void testLoescheFeedback() {

        feedback = new Feedback("12345", fullName, email, message);
        when(feedbackRepository.findById("12345")).thenReturn(feedback);

        Optional<Feedback> geloeschtesFeedback = feedbackService.loescheFeedback("12345");

        assertTrue(geloeschtesFeedback.isPresent());
        assertEquals(feedback, geloeschtesFeedback.get());
        verify(feedbackRepository).deleteById("12345");
    }

    @Test
    void testFindeFeedback() {

        feedback = new Feedback("12345", fullName, email, message);
        when(feedbackRepository.findById("12345")).thenReturn(feedback);

        Feedback gefundenesFeedback = feedbackService.findeFeedback("12345");

        assertNotNull(gefundenesFeedback);
        assertEquals(fullName, gefundenesFeedback.getFullName());
        assertEquals(email, gefundenesFeedback.getEmail());
        assertEquals(message, gefundenesFeedback.getMessage());
    }

    @Test
    void testFindeAlleFeedbacks() {
        Feedback feedback1 = new Feedback("12345", fullName, email, message);
        Feedback feedback2 = new Feedback("67890", new FullName("Max", "Mustermann"), new Email("max.mustermann@example.com"), new Message("Feedback von Max"));
        feedback1.setStatus(Status.RECEIVED);
        feedback2.setStatus(Status.RECEIVED);

        when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2));

        List<Feedback> feedbackList = feedbackService.findeAlleFeedbacks();

        assertNotNull(feedbackList);
        assertEquals(2, feedbackList.size());
        assertTrue(feedbackList.contains(feedback1));
        assertTrue(feedbackList.contains(feedback2));
    }

    @Test
    void testGetAllEmails() {
        Feedback feedback1 = new Feedback("1", new FullName("John", "Doe"), new Email("john.doe@example.com"), new Message("Message 1"));
        Feedback feedback2 = new Feedback("2", new FullName("Jane", "Doe"), new Email("jane.doe@example.com"), new Message("Message 2"));
        when(feedbackRepository.findAll()).thenReturn(List.of(feedback1, feedback2));

        List<String> emails = feedbackService.getAllEmails();

        assertNotNull(emails);
        assertEquals(2, emails.size());
        assertTrue(emails.contains("john.doe@example.com"));
        assertTrue(emails.contains("jane.doe@example.com"));
    }
}





