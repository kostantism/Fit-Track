package gr.hua.dit.fittrack.core.port.impl.dto;

import java.time.Instant;
import java.time.LocalDate;

public record ProgressEntryDTO(
        Long id,
        Long customerId,
        LocalDate entryDate,
        Double weightKg,
        Integer runTimeSeconds,
        String notes,
        Instant createdAt
) {}
