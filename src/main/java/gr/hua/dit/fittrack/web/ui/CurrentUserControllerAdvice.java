package gr.hua.dit.fittrack.web.ui;

import gr.hua.dit.fittrack.core.security.CurrentUser;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Makes the current user available in Thymeleaf templates as "me".
 */
@ControllerAdvice
public class CurrentUserControllerAdvice {

    private final CurrentUserProvider currentUserProvider;

    public CurrentUserControllerAdvice(CurrentUserProvider currentUserProvider) {
        this.currentUserProvider = currentUserProvider;
    }

    @ModelAttribute("me")
    public CurrentUser addCurrentUserToModel() {
        return currentUserProvider.getCurrentUser().orElse(null);
    }
}
