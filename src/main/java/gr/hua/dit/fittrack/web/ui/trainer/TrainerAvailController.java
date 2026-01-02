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
@RequestMapping("/trainer/availability")
public class TrainerAvailController {

    private final AvailabilityService availabilityService;
    private final CurrentUserProvider currentUserProvider;
    private final PersonRepository personRepository;

    public TrainerAvailController(
            AvailabilityService availabilityService,
            CurrentUserProvider currentUserProvider,
            PersonRepository personRepository
    ) {
        this.availabilityService = availabilityService;
        this.currentUserProvider = currentUserProvider;
        this.personRepository = personRepository;
    }

    @GetMapping("/trainer/availability")
    public String showAvailabilityForm(Model model) {
        model.addAttribute("availability", new CreateAvailabilityRequest(null, null));
        return "trainer/availability";
    }

    @PostMapping
    public String createAvailability(
            @Valid @ModelAttribute CreateAvailabilityRequest request,
            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {
            return "trainer/availability";
        }

        CurrentUser currentUser = currentUserProvider.requireCurrentUser();

        Person trainer = personRepository
                .findByEmailAddress(currentUser.emailAddress())
                .orElseThrow(() -> new IllegalStateException("Trainer not found"));

        availabilityService.createAvailability(
                trainer,
                request.startDateTime().toLocalDate(),
                request.startDateTime(),
                request.endDateTime()
        );

        return "redirect:/trainer/availability";
    }


}