package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.TrainerAvailability;
import gr.hua.dit.fittrack.core.service.model.AvailabilitySlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AvailabilityService {


    TrainerAvailability createAvailability(Long trainerId, LocalDate date, LocalDateTime startTime, LocalDateTime endTime);

    List<AvailabilitySlot> getAvailableSlots(
            Long trainerId,
            LocalDate date
    );
}
