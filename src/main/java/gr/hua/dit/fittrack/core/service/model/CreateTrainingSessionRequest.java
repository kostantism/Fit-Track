package gr.hua.dit.fittrack.core.service.model;

import jakarta.validation.constraints.NotNull;

public record CreateTrainingSessionRequest(
        @NotNull(message = "Appointment ID is required")
        Long appointmentId,

        @NotNull(message = "Trainer ID is required")
        Long trainerId,

        @NotNull(message = "Notes cannot be null")
        String notes,

        @NotNull(message = "Training plan cannot be null")
        String trainingPlan

) {}



