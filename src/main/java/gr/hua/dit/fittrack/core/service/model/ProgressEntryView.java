package gr.hua.dit.fittrack.core.service.model;

import java.time.Instant;
import java.time.LocalDate;

public record ProgressEntryView(
        Long id,
        Long customerId,
        LocalDate entryDate,
        Double weightKg,
        Integer runTimeSeconds,
        String notes,
        Instant createdAt
) {}
