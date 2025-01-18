
package status.domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

public class StatusEventListenerTest {

    private StatusEventListener statusEventListener;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        logger = Mockito.mock(Logger.class);
        statusEventListener = new StatusEventListener();
    }

    @Test
    public void testHandleStatusAngepasstEvent() {
        StatusAngepasstEvent event = Mockito.mock(StatusAngepasstEvent.class);
        Mockito.when(event.getFeedbackID()).thenReturn("12345");

        statusEventListener.handleStatusAngepasstEvent(event);

        //Mockito.verify(logger).info("{} empfangen für Feedback-ID: {}", "StatusAngepasstEvent", "12345");
        Mockito.verify(event).processEvent();
    }

    @Test
    public void testHandleStatusEmpfangenEvent() {
        StatusEmpfangenEvent event = Mockito.mock(StatusEmpfangenEvent.class);
        Mockito.when(event.getFeedbackID()).thenReturn("12345");

        statusEventListener.handleStatusEmpfangenEvent(event);

        //Mockito.verify(logger).info("{} empfangen für Feedback-ID: {}", "StatusEmpfangenEvent", "12345");
        Mockito.verify(event).processEvent();
    }

    @Test
    public void testHandleStatusAbgefragtEvent() {
        StatusAbgefragtEvent event = Mockito.mock(StatusAbgefragtEvent.class);
        Mockito.when(event.getFeedbackID()).thenReturn("12345");

        statusEventListener.handleStatusAbgefragtEvent(event);

        //Mockito.verify(logger).info("{} empfangen für Feedback-ID: {}", "StatusAbgefragtEvent", "12345");
        Mockito.verify(event).processEvent();
    }
}