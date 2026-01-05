package gr.hua.dit.fittrack.web.ui.trainer;

import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AvailabilityService;
import gr.hua.dit.fittrack.core.service.model.CreateAvailabilityRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer/availability")
@PreAuthorize("hasRole('TRAINER')")
public class TrainerAvailController {

    private final AvailabilityService availabilityService;
    private final CurrentUserProvider currentUserProvider;

    public TrainerAvailController(
            AvailabilityService availabilityService,
            CurrentUserProvider currentUserProvider
    ) {
        this.availabilityService = availabilityService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public String showAvailabilityForm(Model model) {
        model.addAttribute("availability", new CreateAvailabilityRequest(null, null));
        return "trainer/availability";
    }

    @PostMapping
    public String createAvailability(
            @ModelAttribute CreateAvailabilityRequest request
    ) {
        long trainerId = currentUserProvider.requireTrainerId();

        availabilityService.createAvailability(
                trainerId,
                request.startDateTime().toLocalDate(),
                request.startDateTime(),
                request.endDateTime()
        );

        return "redirect:/trainer/availability";
    }
}