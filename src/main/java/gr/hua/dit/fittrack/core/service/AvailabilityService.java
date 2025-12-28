package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.TrainerAvailabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@Transactional
public class AvailabilityService {

    private final TrainerAvailabilityRepository availabilityRepository;

    public AvailabilityService(TrainerAvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    public TrainerAvailability createAvailability(
            Person trainer,
            LocalDate date,
            LocalDateTime  startTime,
            LocalDateTime endTime) {

        // ❌ Only trainers can define availability
        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Only trainers can define availability");
        }

        // ❌ Invalid time range
        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        // ❌ Past availability
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Availability cannot be in the past");
        }

        // ❌ Overlapping availability
        boolean overlap = availabilityRepository
                .existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        trainer, date, endTime, startTime);

        if (overlap) {
            throw new IllegalStateException("Overlapping availability slot");
        }

        TrainerAvailability availability = new TrainerAvailability(
                trainer,
                date,
                startTime,
                endTime,
                AvailabilityStatus.AVAILABLE
        );

        return availabilityRepository.save(availability);
    }
}
