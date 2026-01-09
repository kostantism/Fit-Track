package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.FitnessGoal;

import java.util.List;

public record CustomerOverviewView(
        Long customerId,
        String customerName,
        FitnessGoal goal,
        String profileNotes,
        List<ProgressEntryView> progress
) {}