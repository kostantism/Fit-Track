package gr.hua.dit.fittrack.web.ui.error;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(
            AccessDeniedException ex,
            Model model
    ) {
        model.addAttribute(
                "errorMessage",
                "Δεν έχετε δικαίωμα για αυτή την ενέργεια."
        );
        return "/error";
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class
    })
    public String handleBusinessErrors(
            RuntimeException ex,
            Model model
    ) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "/error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneric(
            Exception ex,
            Model model
    ) {
        model.addAttribute(
                "errorMessage",
                "Κάτι πήγε στραβά. Παρακαλώ δοκιμάστε ξανά."
        );
        return "/error";
    }
}
