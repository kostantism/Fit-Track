package gr.hua.dit.fittrack.core.service.model;

import java.time.LocalDateTime;

public record AvailabilitySlot(
        Long trainerId,
        LocalDateTime startTime,
        LocalDateTime endTime
) {}