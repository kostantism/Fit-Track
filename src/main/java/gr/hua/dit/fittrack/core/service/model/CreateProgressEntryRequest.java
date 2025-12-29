package gr.hua.dit.fittrack.core.service.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

public record CreateProgressEntryRequest(
        @NotNull(message = "Entry date is required")
        LocalDate entryDate,

        @Positive(message = "Weight must be positive")
        Double weightKg,

        @PositiveOrZero(message = "Run time must be zero or positive")
        Integer runTimeSeconds,

        String notes
) {}

/*
package gr.hua.dit.fittrack.core.service.model; import java.time.LocalDate; public record CreateProgressEntryRequest( LocalDate entryDate, Double weightKg, Integer runTimeSeconds, String notes ) {}

*/
