package gr.hua.dit.fittrack.core.service.model;

import java.time.LocalDate;

public record CreateProgressEntryRequest(
        LocalDate entryDate,
        Double weightKg,
        Integer runTimeSeconds,
        String notes
) {}
