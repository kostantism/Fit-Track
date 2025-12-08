package gr.hua.dit.fittrack.web.ui;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.repository.PersonRepository;

import gr.hua.dit.fittrack.core.service.PersonBusinessLogicService;
import gr.hua.dit.fittrack.core.service.model.CreatePersonRequest;
import gr.hua.dit.fittrack.core.service.model.CreatePersonResult;
import gr.hua.dit.fittrack.core.service.model.PersonView;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

/**
 * UI controller for managing customer/trainer registration.
 */
@Controller
public class RegistrationController {

    private final PersonBusinessLogicService personBusinessLogicService;

    public RegistrationController(final PersonBusinessLogicService personBusinessLogicService) {
        if (personBusinessLogicService == null) throw new NullPointerException();
        this.personBusinessLogicService = personBusinessLogicService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(final Model model) {
        // todo if user is auth, redirect to default view.
        final CreatePersonRequest createPersonRequest = new CreatePersonRequest(
                PersonType.CUSTOMER,
                "",
                "",
                "",
                "",
                ""
        );
        model.addAttribute("createPersonRequest", createPersonRequest);
        return "register";
    }

    @PostMapping("/register")
    public String handleFormSubmission(@ModelAttribute("createPersonRequest") CreatePersonRequest createPersonRequest,
                                       final Model model) {

        // TODO if user is authenticated, redirect to tickets
        // TODO Validate form (email format, size, blank, etc)
        // TODO if form has errors, show the form (with pre-filled data)
        // TODO otherwise, persist person, then, redirect to login

        final CreatePersonResult createPersonResult = this.personBusinessLogicService.createPerson(createPersonRequest);
        if(createPersonResult.created()) {
            return "redirect:/login";
        }
        model.addAttribute("createPersonResult", createPersonResult);
        model.addAttribute("errorMessage", createPersonResult.reason());
        return "register";
    }
}
