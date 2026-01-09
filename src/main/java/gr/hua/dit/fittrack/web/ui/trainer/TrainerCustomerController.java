package gr.hua.dit.fittrack.web.ui.trainer;
import gr.hua.dit.fittrack.core.model.CustomerProfile;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.service.CustomerProfileService;
import gr.hua.dit.fittrack.core.service.PersonDataService;
import gr.hua.dit.fittrack.core.service.ProgressService;
import gr.hua.dit.fittrack.core.service.model.CustomerOverviewView;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trainer/customers")
@PreAuthorize("hasRole('TRAINER')")
public class TrainerCustomerController {

    private final PersonDataService personDataService;
    private final CustomerProfileService profileService;
    private final ProgressService progressService;

    public TrainerCustomerController(
            PersonDataService personDataService,
            CustomerProfileService profileService,
            ProgressService progressService
    ) {
        this.personDataService = personDataService;
        this.profileService = profileService;
        this.progressService = progressService;
    }

    @GetMapping("/{customerId}")
    public String viewCustomer(
            @PathVariable Long customerId,
            Model model
    ) {

        Person customer = personDataService.findPersonEntityById(customerId);

        CustomerProfile profile =
                profileService.getOrCreateProfile(customer);

        List<ProgressEntryView> progress =
                progressService.getProgressForCustomer(customerId);

        CustomerOverviewView overview =
                new CustomerOverviewView(
                        customerId,
                        customer.getFirstName() + " " + customer.getLastName(),
                        profile.getGoal(),
                        profile.getNotes(),
                        progress
                );

        model.addAttribute("customer", overview);
        return "trainer/customerOverview";
    }
}
