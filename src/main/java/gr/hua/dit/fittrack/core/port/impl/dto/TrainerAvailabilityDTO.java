package gr.hua.dit.fittrack.core.port.impl.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record TrainerAvailabilityDTO(
        Long id,
        Long trainerId,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        String status
) {}
