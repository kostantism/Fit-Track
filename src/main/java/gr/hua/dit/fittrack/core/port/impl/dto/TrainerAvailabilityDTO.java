package gr.hua.dit.fittrack.core.port.impl.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TrainerAvailabilityDTO(
        Long id,
        Long trainerId,
        LocalDate date,
        LocalDateTime startTime,
        LocalDateTime  endTime,
        String status
) {}
