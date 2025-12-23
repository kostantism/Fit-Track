package gr.hua.dit.fittrack.core.port.impl.dto;

import java.time.LocalDateTime;

public record TrainingSessionDTO(
        Long id,
        Long trainerId,
        Long customerId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        String notes
) {}
