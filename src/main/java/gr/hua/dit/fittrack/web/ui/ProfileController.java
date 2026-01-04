package gr.hua.dit.fittrack.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * UI controller for profile page.
 * The authenticated user is provided automatically
 * via CurrentUserControllerAdvice as model attribute "me".
 */
@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile() {
        return "profile";
    }
}


