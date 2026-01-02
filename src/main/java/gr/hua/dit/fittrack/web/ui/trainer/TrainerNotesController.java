package gr.hua.dit.fittrack.web.ui.trainer;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.TrainingSessionRepository;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.TrainingSessionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Controller
//@RequestMapping("/trainer/notes")
//@PreAuthorize("hasRole('TRAINER')")
//public class TrainerNotesController {
//
//    private final AppointmentRepository appointmentRepository;
//    private final TrainingSessionRepository sessionRepository;
//    private final TrainingSessionService trainingSessionService;
//    private final PersonRepository personRepository;
//    private final CurrentUserProvider currentUserProvider;
//
//    public TrainerNotesController(
//            AppointmentRepository appointmentRepository,
//            TrainingSessionRepository sessionRepository,
//            TrainingSessionService trainingSessionService,
//            PersonRepository personRepository,
//            CurrentUserProvider currentUserProvider
//    ) {
//        this.appointmentRepository = appointmentRepository;
//        this.sessionRepository = sessionRepository;
//        this.trainingSessionService = trainingSessionService;
//        this.personRepository = personRepository;
//        this.currentUserProvider = currentUserProvider;
//    }
//
//    // 📌 Προβολή notes
//    @GetMapping
//    public String showNotes(Model model) {
//
//        long trainerId = currentUserProvider.requireTrainerId();
//        Person trainer = personRepository.findById(trainerId).orElseThrow();
//
//        List<Appointment> appointments =
//                appointmentRepository.findByTrainerAndStatus(
//                        trainer, AppointmentStatus.APPROVED
//                );
//
//        model.addAttribute("appointments", appointments);
//        return "trainer/notes";
//    }
//
//    // 💾 Αποθήκευση notes + plan
//    @PostMapping("/{appointmentId}")
//    public String saveNotes(
//            @PathVariable Long appointmentId,
//            @RequestParam String notes,
//            @RequestParam(required = false) String trainingPlan
//    ) {
//
//        long trainerId = currentUserProvider.requireTrainerId();
//
//        trainingSessionService.createSession(
//                appointmentId,
//                trainerId,
//                notes
//        );
//
//        // (το trainingPlan μπορείς να το περάσεις εύκολα αν θες,
//        // απλά πρόσθεσε setter μέσα στο service)
//
//        return "redirect:/trainer/notes";
//    }
//}


@Controller
@RequestMapping("/trainer/notes")
@PreAuthorize("hasRole('TRAINER')")
public class TrainerNotesController {

    private final AppointmentService appointmentService;
    private final TrainingSessionService trainingSessionService;
    private final PersonRepository personRepository;
    private final CurrentUserProvider currentUserProvider;

    public TrainerNotesController(
            AppointmentService appointmentService,
            TrainingSessionService trainingSessionService,
            PersonRepository personRepository,
            CurrentUserProvider currentUserProvider
    ) {
        this.appointmentService = appointmentService;
        this.trainingSessionService = trainingSessionService;
        this.personRepository = personRepository;
        this.currentUserProvider = currentUserProvider;
    }

    // 📌 Προβολή notes / πλάνου
    @GetMapping
    public String showNotes(Model model) {

        long trainerId = currentUserProvider.requireTrainerId();
        Person trainer = personRepository.findById(trainerId).orElseThrow();

        List<Appointment> appointments =
                appointmentService.getApprovedAppointmentsForTrainer(trainer);

        model.addAttribute("appointments", appointments);
        return "trainer/notes";
    }

    // 💾 Αποθήκευση notes + plan
    @PostMapping("/{appointmentId}")
    public String saveNotes(
            @PathVariable Long appointmentId,
            @RequestParam String notes,
            @RequestParam String trainingPlan
    ) {
        long trainerId = currentUserProvider.requireTrainerId();

        trainingSessionService.createSession(
                appointmentId,
                trainerId,
                notes,
                trainingPlan
        );

        return "redirect:/trainer/notes";
    }
}
