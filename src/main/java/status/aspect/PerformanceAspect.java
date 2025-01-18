package status.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect // Spring erkennt Logik
@Component // registriert Klasse als Spring-Bean
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);
    public PerformanceAspect() {
        logger.info("PerformanceAspect wurde registriert.");
    }

    //    Ausführungszeit setStatus wird gemessen
    @Around("execution(* status.application.service.StatusService.setStatus(..))")
    public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("PerformanceAspect wurde aufgerufen");
        //Startzeit
        long start = System.currentTimeMillis();
        //Methode ausführen
        Object result = joinPoint.proceed();
        //Zeit nach Ausführen berechnen
        long elapsedTime = System.currentTimeMillis() - start;
        //Log der gemessenen Zeit
        logger.info("{} ausgeführt in {} ms", joinPoint.getSignature().getName(), elapsedTime);
        return result;
    }
}



