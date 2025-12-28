package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.TrainingSessionStatus;

import java.time.Instant;
import java.time.LocalDateTime;

public record TrainingSessionView(
        Long id,
        Long appointmentId,
        Long trainerId,
        Long customerId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        TrainingSessionStatus status,
        String notes,
        Instant createdAt
) {}
