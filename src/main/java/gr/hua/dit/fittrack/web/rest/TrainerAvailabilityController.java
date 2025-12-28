package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.AvailabilityStatus;
import gr.hua.dit.fittrack.core.port.impl.dto.TrainerAvailabilityDTO;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@RequestMapping("/api/availability")
public class TrainerAvailabilityController {

    private final AvailabilityService availabilityService;
    private final CurrentUserProvider currentUserProvider;
    private final PersonRepository personRepository;

    public TrainerAvailabilityController(
            AvailabilityService availabilityService,
            CurrentUserProvider currentUserProvider,
            PersonRepository personRepository) {
        this.availabilityService = availabilityService;
        this.currentUserProvider = currentUserProvider;
        this.personRepository = personRepository;
    }

    @PostMapping
    public TrainerAvailabilityDTO createAvailability(
            @RequestParam LocalDate date,
            @RequestParam LocalDateTime  startTime,
            @RequestParam LocalDateTime endTime
    ) {
        long trainerId = currentUserProvider.requireTrainerId();
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        var availability = availabilityService.createAvailability(trainer, date, startTime, endTime);

        return new TrainerAvailabilityDTO(
                availability.getId(),
                trainer.getId(),
                availability.getDate(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getStatus().name()
        );
    }
}
