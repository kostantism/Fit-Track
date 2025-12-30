package gr.hua.dit.fittrack.core.port.impl.dto;

public record TrainerPublicView(
        Long trainerId,
        String firstName,
        String lastName,
        String specialization,
        String area,
        String bio
) {}
