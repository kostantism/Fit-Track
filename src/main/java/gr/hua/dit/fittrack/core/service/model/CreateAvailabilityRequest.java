package gr.hua.dit.fittrack.core.service.model;

import java.time.LocalDateTime;

public record CreateAvailabilityRequest(
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {}
