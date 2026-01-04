package gr.hua.dit.fittrack.core.service.model;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateAvailabilityRequest(
        @NotNull(message = "Start date/time is required")
        @FutureOrPresent(message = "Start date/time must be in the present or future")
        LocalDateTime startDateTime,

        @NotNull(message = "End date/time is required")
        @FutureOrPresent(message = "End date/time must be in the present or future")
        LocalDateTime endDateTime
) {}



