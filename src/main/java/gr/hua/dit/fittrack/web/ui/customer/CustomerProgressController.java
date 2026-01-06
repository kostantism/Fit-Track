package gr.hua.dit.fittrack.web.ui.customer;

import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.ProgressService;
import gr.hua.dit.fittrack.core.service.model.CreateProgressEntryRequest;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/customer/progress")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerProgressController {

    private final ProgressService progressService;
    private final CurrentUserProvider currentUserProvider;

    public CustomerProgressController(
            ProgressService progressService,
            CurrentUserProvider currentUserProvider
    ) {
        this.progressService = progressService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public String showProgress(Model model) {

        long customerId = currentUserProvider.requireCustomerId();

        List<ProgressEntryView> progress =
                progressService.getProgressForCustomer(customerId);

        model.addAttribute("progress", progress);

        model.addAttribute(
                "progressForm",
                new CreateProgressEntryRequest(
                        LocalDate.now(),
                        null,
                        null,
                        null
                )
        );

        return "customer/progress";
    }

    @PostMapping
    public String saveProgress(
            @Valid @ModelAttribute("progressForm") CreateProgressEntryRequest request,
            BindingResult bindingResult,
            Model model
    ) {
        long customerId = currentUserProvider.requireCustomerId();

        if (bindingResult.hasErrors()) {
            model.addAttribute(
                    "progress",
                    progressService.getProgressForCustomer(customerId)
            );
            return "customer/progress";
        }

        progressService.createProgressEntry(customerId, request);

        return "redirect:/customer/progress";
    }
}
