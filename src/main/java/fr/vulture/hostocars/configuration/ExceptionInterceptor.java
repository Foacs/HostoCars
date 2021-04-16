package fr.vulture.hostocars.configuration;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Interceptor for exceptions.
 */
@Slf4j
@ControllerAdvice
@ConditionalOnProperty("spring.profiles.active")
public class ExceptionInterceptor {

    /**
     * Resolves the intercepted exception by logging it and returning a {@code 500} HTTP response with the exception as the body.
     *
     * @param exception
     *     The intercepted exception
     *
     * @return a {@code 500} HTTP response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> resolve(final Exception exception) {
        log.error("An error occurred", exception);

        final String className = exception.getClass().getSimpleName();
        final String message = exception.getLocalizedMessage();
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(Objects.isNull(message) ? className : className + ": " + message);
    }

}
