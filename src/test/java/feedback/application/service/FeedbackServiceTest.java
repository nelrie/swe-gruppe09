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
import feedback.infrastructure.repository.MockFeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import status.application.service.StatusService;
import status.infrastructure.repository.MockStatusRepository;
import status.infrastructure.repository.StatusRepository;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

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
    public void testErstelleFeedback() {
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
    public void testLoescheFeedback() {

        feedback = new Feedback("12345", fullName, email, message);
        when(feedbackRepository.findById("12345")).thenReturn(feedback);

        feedbackService.loescheFeedback("12345");

        verify(feedbackRepository).deleteById("12345");
//
    }

    @Test
    public void testFindeFeedback() {

        feedback = new Feedback("12345", fullName, email, message);
        when(feedbackRepository.findById("12345")).thenReturn(feedback);

        Feedback gefundenesFeedback = feedbackService.findeFeedback("12345");

        assertNotNull(gefundenesFeedback);
        assertEquals(fullName, gefundenesFeedback.getFullName());
        assertEquals(email, gefundenesFeedback.getEmail());
        assertEquals(message, gefundenesFeedback.getMessage());

    }

}



