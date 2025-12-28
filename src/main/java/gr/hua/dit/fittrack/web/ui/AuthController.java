package gr.hua.dit.fittrack.web.ui;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * UI controller for user authentication (login and logout).
 */
@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Authentication authentication) {
        // TODO If user is authenticated, redirect to default view.

        if (authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/profile";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        // TODO If user is not authenticated, redirect to login.
        return "logout";
    }
}
