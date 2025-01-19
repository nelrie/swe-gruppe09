package feedback.infrastructure;

import feedback.ui.controller.SharedUIController;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

public class SharedControllerTest {

    @Mock
    private SharedUIController sharedUIController;

    @Mock
    private ActionEvent actionEvent;

    @InjectMocks
    private SharedController sharedController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOpenFeedbackForm() {
        sharedController.openFeedbackForm(actionEvent);
        verify(sharedUIController, times(1)).openFeedbackForm(actionEvent);
    }

    @Test
    public void testOpenStatusPage() {
        sharedController.openStatusPage(actionEvent);
        verify(sharedUIController, times(1)).openStatusPage(actionEvent);
    }

    @Test
    public void testOpenStartPage() {
        sharedController.openStartPage(actionEvent);
        verify(sharedUIController, times(1)).openStartPage(actionEvent);
    }
}