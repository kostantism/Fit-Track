package gr.hua.dit.fittrack.web.rest.error;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.Instant;

/**
 * Global REST API error handler.
 */
@RestControllerAdvice(basePackages = "gr.hua.dit.fittrack.web.rest")
@Order(1)
public class GlobalErrorHandlerRestControllerAdvice {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalErrorHandlerRestControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAnyError(final Exception exception,
                                                   final HttpServletRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        if (exception instanceof NoResourceFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (exception instanceof SecurityException) {
            status = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof AuthorizationDeniedException) {
            status = HttpStatus.FORBIDDEN;
        } else if (exception instanceof ResponseStatusException ex) {
            try {
                status = HttpStatus.valueOf(ex.getStatusCode().value());
            } catch (Exception ignored) {}
        }

        LOGGER.warn("REST error [{} {}] -> {} ({})",
                request.getMethod(),
                request.getRequestURI(),
                status.value(),
                exception.getClass().getSimpleName()
        );

        ApiError apiError = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(status).body(apiError);
    }
}
