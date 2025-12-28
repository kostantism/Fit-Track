package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.AppointmentStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public record AppointmentView(
        Long id,
        Long trainerId,
        Long customerId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        AppointmentStatus status,
        Instant createdAt
) {}
