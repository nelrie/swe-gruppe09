package status.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

class PerformanceAspectTest {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspectTest.class);

    @Test
    void monitorPerformanceTest() throws Throwable {
        // Mock des ProceedingJoinPoints erstellen
        ProceedingJoinPoint mockJoinPoint = mock(ProceedingJoinPoint.class);

        // Mock für die Signature konfigurieren
        Signature mockSignature = mock(Signature.class);
        when(mockJoinPoint.getSignature()).thenReturn(mockSignature);
        when(mockSignature.getName()).thenReturn("setStatus");

        // Simuliere die Zielmethode
        when(mockJoinPoint.proceed()).thenAnswer(invocation -> {
            // Simuliere eine Aktion, die ausgeführt wird und "Zeit benötigt"
            logger.info("Zielmethode wurde ausgeführt");
            return null; // Für Methoden ohne Rückgabewert
        });

        // PerformanceAspect-Instanz erstellen
        PerformanceAspect aspect = new PerformanceAspect();

        // Systemzeit vor und nach dem Aufruf
        long startTime = System.currentTimeMillis();
        aspect.monitorPerformance(mockJoinPoint);
        long elapsedTime = System.currentTimeMillis() - startTime;

        // Verifiziere, dass mockJoinPoint.proceed() 1x aufgerufen wurde
        verify(mockJoinPoint, times(1)).proceed();

        // Verifiziere die gemessene Ausführungszeit ist im Testlog korrekt
        logger.info("PerformanceAspect wurde im Test erfolgreich ausgeführt. Gemessene Zeit: {} ms", elapsedTime);
        }
    }
