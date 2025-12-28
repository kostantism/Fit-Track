package gr.hua.dit.fittrack.core.service.model;

public record CreateTrainingSessionRequest(
        Long appointmentId,
        String notes,
        String trainingPlan
) {}
