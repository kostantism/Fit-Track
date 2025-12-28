package gr.hua.dit.fittrack.core.service.model;

import java.time.LocalDateTime;

public record CreateAppointmentRequest(
        Long trainerId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {}
