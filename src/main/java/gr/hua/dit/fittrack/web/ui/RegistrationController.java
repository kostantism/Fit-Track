package gr.hua.dit.fittrack.web.ui;

import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.service.PersonBusinessLogicService;
import gr.hua.dit.fittrack.core.service.model.CreatePersonRequest;
import gr.hua.dit.fittrack.core.service.model.CreatePersonResult;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;

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
    public String showRegistrationForm(
        final Authentication authentication,
        final Model model) {

        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/profile";
        }

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
    public String handleFormSubmission(
        final Authentication authentication,
        @Valid @ModelAttribute("createPersonRequest") CreatePersonRequest createPersonRequest,
        final BindingResult bindingResult, // IMPORTANT: BindingResult **MUST** come immediately after the @Valid argument!
        final Model model) {

        if (AuthUtils.isAuthenticated(authentication)) {
            return "redirect:/profile"; // already logged in.
        }

        if (bindingResult.hasErrors()) {
            return "register";
        }

        final CreatePersonResult createPersonResult = this.personBusinessLogicService.createPerson(createPersonRequest);
        if(createPersonResult.created()) {
            return "redirect:/login"; // registration successful
        }
        model.addAttribute("createPersonResult", createPersonResult);
        model.addAttribute("errorMessage", createPersonResult.reason());
        return "register";
    }

    /// ////////////////////////////
}
