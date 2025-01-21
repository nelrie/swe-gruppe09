
package status.domain.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;

class StatusEventListenerTest {

    private StatusEventListener statusEventListener;
    private Logger logger;

    @BeforeEach
    public void setUp() {
        logger = Mockito.mock(Logger.class);
        statusEventListener = new StatusEventListener();
    }

    @Test
    void testHandleStatusAngepasstEvent() {
        StatusAngepasstEvent event = Mockito.mock(StatusAngepasstEvent.class);
        Mockito.when(event.getFeedbackID()).thenReturn("12345");

        statusEventListener.handleStatusAngepasstEvent(event);

        Mockito.verify(event).processEvent();
    }

    @Test
    void testHandleStatusEmpfangenEvent() {
        StatusEmpfangenEvent event = Mockito.mock(StatusEmpfangenEvent.class);
        Mockito.when(event.getFeedbackID()).thenReturn("12345");

        statusEventListener.handleStatusEmpfangenEvent(event);


        Mockito.verify(event).processEvent();
    }

    @Test
    void testHandleStatusAbgefragtEvent() {
        StatusAbgefragtEvent event = Mockito.mock(StatusAbgefragtEvent.class);
        Mockito.when(event.getFeedbackID()).thenReturn("12345");

        statusEventListener.handleStatusAbgefragtEvent(event);

        Mockito.verify(event).processEvent();
    }
}