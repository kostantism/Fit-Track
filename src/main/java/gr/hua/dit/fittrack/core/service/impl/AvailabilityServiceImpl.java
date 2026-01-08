package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.TrainerAvailabilityRepository;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.service.model.AvailabilitySlot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AvailabilityServiceImpl implements AvailabilityService {

    private final TrainerAvailabilityRepository availabilityRepository;
    private final PersonRepository personRepository;
    private final PersonDataService personDataService;
    private final AppointmentRepository appointmentRepository;

    public AvailabilityServiceImpl(
            TrainerAvailabilityRepository availabilityRepository,
            PersonRepository personRepository,
            PersonDataService personDataService,
            AppointmentRepository appointmentRepository
    ) {
        this.availabilityRepository = availabilityRepository;
        this.personRepository = personRepository;
        this.personDataService = personDataService;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public TrainerAvailability createAvailability(
            Long trainerId,
            LocalDate date,
            LocalDateTime startTime,
            LocalDateTime endTime
    ) {

        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Only trainers can define availability");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Availability cannot be in the past");
        }
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


//    public List<AvailabilitySlot> getAvailableSlots(Long trainerId, LocalDate date) {
//
//        Person trainer = personDataService.findPersonEntityById(trainerId);
//
//        return availabilityRepository
//                .findByTrainerAndDateAndStatus(trainer, date, AvailabilityStatus.AVAILABLE)
//                .stream()
//                .map(a -> new AvailabilitySlot(a.getStartTime(), a.getEndTime()))
//                .toList();
//    }

    @Override
    public List<AvailabilitySlot> getAvailableSlots(Long trainerId, LocalDate date) {

        Person trainer = personDataService.findPersonEntityById(trainerId);

        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Not a trainer");
        }

        // 1️⃣ Παίρνουμε τα availability blocks
        List<TrainerAvailability> availabilities =
                availabilityRepository.findByTrainerAndDateAndStatus(
                        trainer,
                        date,
                        AvailabilityStatus.AVAILABLE
                );

        // 2️⃣ Παίρνουμε τα ήδη κλεισμένα appointments
        List<Appointment> appointments =
                appointmentRepository.findByTrainer(trainer);

        // 3️⃣ Φιλτράρουμε όσα ΔΕΝ συγκρούονται
        return availabilities.stream()
                .filter(a ->
                        appointments.stream().noneMatch(app ->
                                app.getStartDateTime().isBefore(a.getEndTime())
                                        && app.getEndDateTime().isAfter(a.getStartTime())
                        )
                )
                .map(a -> new
                        AvailabilitySlot(
                        trainerId,
                        a.getStartTime(),
                        a.getEndTime()
                ))
                .toList();
    }
}
