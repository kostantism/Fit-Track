package gr.hua.dit.fittrack.web.ui.trainer;

//@Controller
//@RequestMapping("/trainer/notes")
//@PreAuthorize("hasRole('TRAINER')")
//public class TrainerNotesController {
//
//    private final AppointmentRepository appointmentRepository;
//    private final TrainingSessionService trainingSessionService;
//    private final CurrentUserProvider currentUserProvider;
//    private final PersonRepository personRepository;
//
//    public TrainerNotesController(
//            AppointmentRepository appointmentRepository,
//            TrainingSessionService trainingSessionService,
//            CurrentUserProvider currentUserProvider,
//            PersonRepository personRepository
//    ) {
//        this.appointmentRepository = appointmentRepository;
//        this.trainingSessionService = trainingSessionService;
//        this.currentUserProvider = currentUserProvider;
//        this.personRepository = personRepository;
//    }
//
//    // 📌 Προβολή approved appointments για notes
//    @GetMapping
//    public String showNotes(Model model) {
//
//        long trainerId = currentUserProvider.requireTrainerId();
//        Person trainer = personRepository.findById(trainerId).orElseThrow();
//
//        // ✅ ΕΔΩ μπαίνει αυτό που ρώτησες
//        List<Appointment> appointments =
//                appointmentRepository.findByTrainerAndStatus(
//                        trainer,
//                        AppointmentStatus.APPROVED
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
//            @RequestParam String trainingPlan
//    ) {
//        long trainerId = currentUserProvider.requireTrainerId();
//        trainingSessionService.createSession(
//                appointmentId,
//                trainerId,
//                notes,
//                trainingPlan
//        );
//        return "redirect:/trainer/notes";
//    }
//}

//@Controller
//@RequestMapping("/trainer/notes")
//@PreAuthorize("hasRole('TRAINER')")
//public class TrainerNotesController {
//
//    private final AppointmentService appointmentService;
//    private final TrainingSessionService trainingSessionService;
//    private final CurrentUserProvider currentUserProvider;
////    private final PersonRepository personRepository;
//    private final PersonDataService personDataService;
//
//    public TrainerNotesController(
//            AppointmentService appointmentService,
//            TrainingSessionService trainingSessionService,
//            CurrentUserProvider currentUserProvider,
////            PersonRepository personRepository
//            PersonDataService personDataService
//    ) {
//        this.appointmentService = appointmentService;
//        this.trainingSessionService = trainingSessionService;
//        this.currentUserProvider = currentUserProvider;
////        this.personRepository = personRepository;
//        this.personDataService = personDataService;
//    }
//

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.TrainingSessionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

////    @GetMapping
////    public String showNotes(Model model) {
////
////        long trainerId = currentUserProvider.requireTrainerId();
////        Person trainer = personRepository.findById(trainerId).orElseThrow();
////
////        List<Appointment> appointments =
////                appointmentService.getApprovedAppointmentsForTrainer(trainer);
////
////        model.addAttribute("appointments", appointments);
////        return "trainer/notes";
////    }
//
//    @GetMapping
//    public String showNotes(Model model) {
//
//        long trainerId = currentUserProvider.requireTrainerId();
//
//        List<Appointment> appointments =
//                appointmentService.getApprovedAppointmentsForTrainer(trainerId);
//
//        model.addAttribute("appointments", appointments);
//        return "trainer/notes";
//    }
//
//    @PostMapping("/{appointmentId}")
//    public String saveNotes(
//            @PathVariable Long appointmentId,
//            @RequestParam String notes,
//            @RequestParam String trainingPlan
//    ) {
//        long trainerId = currentUserProvider.requireTrainerId();
//
//        trainingSessionService.createSession(
//                appointmentId,
//                trainerId,
//                notes,
//                trainingPlan
//        );
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
    private final CurrentUserProvider currentUserProvider;

    public TrainerNotesController(
            AppointmentService appointmentService,
            TrainingSessionService trainingSessionService,
            CurrentUserProvider currentUserProvider
    ) {
        this.appointmentService = appointmentService;
        this.trainingSessionService = trainingSessionService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public String showNotes(Model model) {

        long trainerId = currentUserProvider.requireTrainerId();

        List<Appointment> appointments =
                appointmentService.getApprovedAppointmentsForTrainer(trainerId);

        model.addAttribute("appointments", appointments);
        return "trainer/notes";
    }

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
