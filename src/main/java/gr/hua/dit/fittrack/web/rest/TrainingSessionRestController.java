package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.TrainingSession;
import gr.hua.dit.fittrack.core.port.impl.dto.TrainingSessionDTO;
import gr.hua.dit.fittrack.core.service.TrainingSessionService;
import gr.hua.dit.fittrack.core.service.model.CreateTrainingSessionRequest;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/training-sessions")
public class TrainingSessionRestController {

    private final TrainingSessionService trainingSessionService;

    public TrainingSessionRestController(TrainingSessionService trainingSessionService) {
        this.trainingSessionService = trainingSessionService;
    }

    // 🔹 Get all sessions
    @GetMapping
    public List<TrainingSessionDTO> getAllSessions() {
        return trainingSessionService.getAllSessions().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Get session by id
    @GetMapping("/{id}")
    public TrainingSessionDTO getSession(@PathVariable Long id) {
        TrainingSession session = trainingSessionService.getSessionById(id);
        return toDTO(session);
    }

    // 🔹 Create new session
    @PostMapping
    public TrainingSessionDTO createSession(@Valid @RequestBody CreateTrainingSessionRequest request) {
        TrainingSession session = trainingSessionService.createSession(
                request.appointmentId(),
                request.trainerId(),
                request.notes(),
                request.trainingPlan()
        );
        return toDTO(session);
    }

    // 🔹 Delete session
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        trainingSessionService.deleteSession(id);
    }

    // 🔹 Convert entity to DTO
    private TrainingSessionDTO toDTO(TrainingSession session) {
        Long customerId = session.getCustomer() != null ? session.getCustomer().getId() : null;
        return new TrainingSessionDTO(
                session.getId(),
                session.getTrainer().getId(),
                customerId,
                session.getStartTime(),
                session.getEndTime(),
                session.getNotes()
        );
    }
}



/*
package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.TrainingSession;
import gr.hua.dit.fittrack.core.port.impl.dto.TrainingSessionDTO;
import gr.hua.dit.fittrack.core.service.TrainingSessionService;
import gr.hua.dit.fittrack.core.security.CurrentUser;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/training-sessions")
public class TrainingSessionRestController {

    private final TrainingSessionService trainingSessionService;
    private final CurrentUserProvider currentUserProvider;

    public TrainingSessionRestController(TrainingSessionService trainingSessionService,
                                         CurrentUserProvider currentUserProvider) {
        this.trainingSessionService = trainingSessionService;
        this.currentUserProvider = currentUserProvider;
    }

    // 🔹 Get all sessions
    @GetMapping
    public List<TrainingSessionDTO> getAllSessions() {
        return trainingSessionService.getAllSessions().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 🔹 Get session by id
    @GetMapping("/{id}")
    public TrainingSessionDTO getSession(@PathVariable Long id) {
        TrainingSession session = trainingSessionService.getSessionById(id);
        return toDTO(session);
    }

    // 🔹 Create new session
    @PostMapping
    public TrainingSessionDTO createSession(
            @RequestParam Long appointmentId,
            @RequestParam Long trainerId,
            @RequestParam String notes
    ) {
        TrainingSession session = trainingSessionService.createSession(
                appointmentId,
                trainerId,
                notes
        );

        return toDTO(session);
    }

    */
/*@PostMapping
    public TrainingSessionDTO createSession(@RequestParam Long trainerId,
                                            @RequestParam String notes,
                                            @RequestParam String startTime,
                                            @RequestParam String endTime) {
        TrainingSession session = trainingSessionService.createSession(
                appointmentId,  // το id του appointment που είναι APPROVED
                trainerId,      // το id του trainer που κάνει το session
                notes           // οι σημειώσεις του trainer
        );

        return toDTO(session);
    }*//*


    // 🔹 Delete session
    @DeleteMapping("/{id}")
    public void deleteSession(@PathVariable Long id) {
        trainingSessionService.deleteSession(id);
    }

    // 🔹 Convert entity to DTO
    private TrainingSessionDTO toDTO(TrainingSession session) {
        Long customerId = session.getCustomer() != null ? session.getCustomer().getId() : null;
        return new TrainingSessionDTO(
                session.getId(),
                session.getTrainer().getId(),
                customerId,
                session.getStartTime(),
                session.getEndTime(),
                session.getNotes()
        );
    }
}
*/
