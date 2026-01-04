package gr.hua.dit.fittrack.core.service.impl;

//@Service
//@Transactional
//public class AvailabilityServiceImpl implements AvailabilityService {
//
//    private final TrainerAvailabilityRepository availabilityRepository;
//
//    public AvailabilityServiceImpl(TrainerAvailabilityRepository availabilityRepository) {
//        this.availabilityRepository = availabilityRepository;
//    }
//
//    @Override
//    public TrainerAvailability createAvailability(Person trainer, LocalDate date, LocalDateTime startTime, LocalDateTime endTime) {
//
//        // ❌ Only trainers can define availability
//        if (trainer.getType() != PersonType.TRAINER) {
//            throw new IllegalArgumentException("Only trainers can define availability");
//        }
//
//        // ❌ Invalid time range
//        if (!startTime.isBefore(endTime)) {
//            throw new IllegalArgumentException("Start time must be before end time");
//        }
//
//        // ❌ Past availability
//        if (date.isBefore(LocalDate.now())) {
//            throw new IllegalArgumentException("Availability cannot be in the past");
//        }
//
//        // ❌ Overlapping availability
//        boolean overlap = availabilityRepository
//                .existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
//                        trainer, date, endTime, startTime);
//
//        if (overlap) {
//            throw new IllegalStateException("Trainer already has availability for this time range");
//        }
//
//        TrainerAvailability availability = new TrainerAvailability(
//                trainer,
//                date,
//                startTime,
//                endTime,
//                AvailabilityStatus.AVAILABLE
//        );
//
//        return availabilityRepository.save(availability);
//    }
//}

import gr.hua.dit.fittrack.core.model.AvailabilityStatus;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.model.TrainerAvailability;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.TrainerAvailabilityRepository;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private final TrainerAvailabilityRepository availabilityRepository;
    private final PersonRepository personRepository;

    public AvailabilityServiceImpl(
            TrainerAvailabilityRepository availabilityRepository,
            PersonRepository personRepository
    ) {
        this.availabilityRepository = availabilityRepository;
        this.personRepository = personRepository;
    }

    @Override
    public TrainerAvailability createAvailability(
            Long trainerId,
            LocalDate date,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        // 🔎 φόρτωση trainer
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // ❌ Only trainers
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
        boolean overlap =
                availabilityRepository.existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        trainer,
                        date,
                        endTime,
                        startTime
                );

        if (overlap) {
            throw new IllegalStateException("Trainer already has availability for this time range");
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