package gr.hua.dit.fittrack.core.port.impl.dto;

import java.time.LocalDateTime;

public record TrainingSessionDTO(
        Long id,
        Long trainerId,
        String title,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {}
