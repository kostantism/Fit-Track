package gr.hua.dit.fittrack.core.port.impl.dto;

import java.time.LocalDateTime;

public record AppointmentDTO(
        Long id,
        Long customerId,
        Long trainerId,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String status
) {}
