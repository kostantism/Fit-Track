package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.port.impl.dto.ProgressEntryDTO;
import gr.hua.dit.fittrack.core.service.ProgressService;
import gr.hua.dit.fittrack.core.service.model.CreateProgressEntryRequest;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/progress")
public class ProgressEntryController {

    private final ProgressService progressService;
    private final CurrentUserProvider currentUserProvider;

    public ProgressEntryController(ProgressService progressService,
                                   CurrentUserProvider currentUserProvider) {
        this.progressService = progressService;
        this.currentUserProvider = currentUserProvider;
    }

    // 🔹 Create new progress entry
    @PostMapping
    public ProgressEntryDTO createProgressEntry(@Valid @RequestBody CreateProgressEntryRequest request) {
        Long customerId = currentUserProvider.requireCustomerId();
        ProgressEntryView entry = progressService.createProgressEntry(customerId, request);
        return toDTO(entry);
    }

    // 🔹 Get all progress entries for current user
    @GetMapping
    public List<ProgressEntryDTO> getAllProgressEntries() {
        Long customerId = currentUserProvider.requireCustomerId();
        return progressService.getProgressForCustomer(customerId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Convert ProgressEntryView to DTO
    private ProgressEntryDTO toDTO(ProgressEntryView view) {
        return new ProgressEntryDTO(
                view.id(),
                view.customerId(),
                view.entryDate(),
                view.weightKg(),
                view.runTimeSeconds(),
                view.notes(),
                view.createdAt()
        );
    }
}
