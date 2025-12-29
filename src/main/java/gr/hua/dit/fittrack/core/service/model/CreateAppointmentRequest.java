package gr.hua.dit.fittrack.core.service.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        @NotNull(message = "Trainer ID is required")
        Long trainerId,

        @NotNull(message = "Start date/time is required")
        @FutureOrPresent(message = "Start date/time must be in the present or future")
        LocalDateTime startDateTime,

        @NotNull(message = "End date/time is required")
        @FutureOrPresent(message = "End date/time must be in the present or future")
        LocalDateTime endDateTime
) {}

/*
package gr.hua.dit.fittrack.core.service.model; import java.time.LocalDateTime; public record CreateAppointmentRequest( Long trainerId, LocalDateTime startDateTime, LocalDateTime endDateTime ) {}*/
