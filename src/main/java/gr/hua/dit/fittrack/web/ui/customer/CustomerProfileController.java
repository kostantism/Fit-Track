package gr.hua.dit.fittrack.web.ui.customer;

import gr.hua.dit.fittrack.core.model.CustomerProfile;
import gr.hua.dit.fittrack.core.model.FitnessGoal;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.CustomerProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer/customerProfile")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerProfileController {

    private final CustomerProfileService profileService;
    private final PersonRepository personRepository;
    private final CurrentUserProvider currentUserProvider;

    public CustomerProfileController(
            CustomerProfileService profileService,
            PersonRepository personRepository,
            CurrentUserProvider currentUserProvider
    ) {
        this.profileService = profileService;
        this.personRepository = personRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public String showProfile(Model model) {

        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId).orElseThrow();

        CustomerProfile profile =
                profileService.getOrCreateProfile(customer);

        model.addAttribute("profile", profile);
        model.addAttribute("goals", FitnessGoal.values());

        return "customer/customerProfile";
    }

    @PostMapping
    public String updateProfile(
            @RequestParam FitnessGoal goal,
            @RequestParam(required = false) String notes
    ) {
        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId).orElseThrow();

        profileService.updateProfile(customer, goal, notes);

        return "redirect:/customer/customerProfile";
    }
}

