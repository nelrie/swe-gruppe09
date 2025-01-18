package status.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class PerformanceAspectTest {

    @Test
    void monitorPerformanceTest() throws Throwable {
        // Mock des ProceedingJoinPoints erstellen
        ProceedingJoinPoint mockJoinPoint = mock(ProceedingJoinPoint.class);

        // Mock für die Signature konfigurieren
        Signature mockSignature = mock(Signature.class);
        when(mockJoinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getName()).thenReturn("setStatus");

        // Simuliere den Ablauf der Zielmethode (z. B. Verzögerung durch Sleep)
        when(mockJoinPoint.proceed()).thenAnswer(invocation -> {
            // Beispiel: Die aufgerufene Methode benötigt 50ms
            Thread.sleep(50);
            return null; // Für Methoden ohne Rückgabewert
        });

        // PerformanceAspect-Instanz erstellen
        PerformanceAspect aspect = new PerformanceAspect();

        // Aspect aufrufen
        aspect.monitorPerformance(mockJoinPoint);

        // Verifiziere, dass mockJoinPoint.proceed() 1x aufgerufen wurde
        verify(mockJoinPoint, times(1)).proceed();

        // Zusätzliche Konsolenausgabe zur Validierung
        System.out.println("PerformanceAspect wurde im Test erfolgreich ausgeführt.");
    }
}