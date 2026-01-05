package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.TrainerAvailability;
import gr.hua.dit.fittrack.core.port.impl.dto.TrainerAvailabilityDTO;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.service.model.CreateAvailabilityRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/availability")
public class TrainerAvailabilityController {

    private final AvailabilityService availabilityService;
    private final CurrentUserProvider currentUserProvider;

    public TrainerAvailabilityController(
            AvailabilityService availabilityService,
            CurrentUserProvider currentUserProvider
    ) {
        this.availabilityService = availabilityService;
        this.currentUserProvider = currentUserProvider;
    }

    @PostMapping
    public TrainerAvailabilityDTO createAvailability(
            @Valid @RequestBody CreateAvailabilityRequest request
    ) {
        long trainerId = currentUserProvider.requireTrainerId();

        TrainerAvailability availability =
                availabilityService.createAvailability(
                        trainerId,
                        request.startDateTime().toLocalDate(),
                        request.startDateTime(),
                        request.endDateTime()
                );

        return new TrainerAvailabilityDTO(
                availability.getId(),
                trainerId,
                availability.getDate(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getStatus().name()
        );
    }
}



