package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.AvailabilityStatus;

import java.time.LocalDateTime;

public record AvailabilityView(
        Long id,
        Long trainerId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        AvailabilityStatus status
) {}
