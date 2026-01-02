package gr.hua.dit.fittrack.web.ui.trainer;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.security.CurrentUser;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.service.model.CreateAvailabilityRequest;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@PreAuthorize("hasRole('TRAINER')")
public class TrainerAvailabilityController {

    private final AvailabilityService availabilityService;
    private final CurrentUserProvider currentUserProvider;
    private final PersonRepository personRepository;

    public TrainerAvailabilityController(
            AvailabilityService availabilityService,
            CurrentUserProvider currentUserProvider,
            PersonRepository personRepository
    ) {
        this.availabilityService = availabilityService;
        this.currentUserProvider = currentUserProvider;
        this.personRepository = personRepository;
    }

    @PostMapping("/trainer/availability")
    public String createAvailability(@ModelAttribute CreateAvailabilityRequest request) {

        CurrentUser currentUser = currentUserProvider.getCurrentUser()
                .orElseThrow(() -> new RuntimeException("User not authenticated"));

        Person trainer = personRepository
                .findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        availabilityService.createAvailability(
                trainer,
                request.startDateTime().toLocalDate(),
                request.startDateTime(),
                request.endDateTime()
        );

        return "redirect:/trainer/availability";
    }
}