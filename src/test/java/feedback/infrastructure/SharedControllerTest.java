package feedback.infrastructure;

import feedback.ui.controller.SharedUIController;
import javafx.event.ActionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class SharedControllerTest {

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
    void testOpenFeedbackForm() {
        sharedController.openFeedbackForm(actionEvent);
        verify(sharedUIController, times(1)).openFeedbackForm(actionEvent);
    }

    @Test
    void testOpenStatusPage() {
        sharedController.openStatusPage(actionEvent);
        verify(sharedUIController, times(1)).openStatusPage(actionEvent);
    }

    @Test
    void testOpenStartPage() {
        sharedController.openStartPage(actionEvent);
        verify(sharedUIController, times(1)).openStartPage(actionEvent);
    }
}