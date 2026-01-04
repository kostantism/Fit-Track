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
                "You don't have permission to access this resource."
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
                "Something went wrong. Please try again."
        );
        return "/error";
    }
}
