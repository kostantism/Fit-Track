//package gr.hua.dit.fittrack.web.ui.trainer;
//
//import gr.hua.dit.fittrack.core.model.Appointment;
//import gr.hua.dit.fittrack.core.model.AppointmentStatus;
//import gr.hua.dit.fittrack.core.model.Person;
//
//import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
//import gr.hua.dit.fittrack.core.repository.TrainingSessionRepository;
//
//import gr.hua.dit.fittrack.core.service.TrainingSessionService;
//import gr.hua.dit.fittrack.core.service.PersonDataService;
//
//import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@Controller
//@RequestMapping("/trainer/notes")
//@PreAuthorize("hasRole('TRAINER')")
////public class TrainerNotesController {
////
////    private final AppointmentRepository appointmentRepository;
////    private final TrainingSessionRepository sessionRepository;
////    private final TrainingSessionService sessionService;
////    private final PersonDataService personDataService;
////    private final CurrentUserProvider currentUser;
////
////    public TrainerNotesController(
////            AppointmentRepository appointmentRepository,
////            TrainingSessionRepository sessionRepository,
////            TrainingSessionService sessionService,
////            PersonDataService personDataService,
////            CurrentUserProvider currentUser) {
////
////        this.appointmentRepository = appointmentRepository;
////        this.sessionRepository = sessionRepository;
////        this.sessionService = sessionService;
////        this.personDataService = personDataService;
////        this.currentUser = currentUser;
////    }
////
////    @GetMapping
////    public String notes(Model model) {
////
////        Long trainerId = currentUser.requireTrainerId();
////        Person trainer = personDataService.findPersonEntityById(trainerId);
////
////        //  μόνο APPROVED appointments
////        List<Appointment> appointments =
////                appointmentRepository.findByTrainerAndStatus(
////                        trainer, AppointmentStatus.APPROVED);
////
////        model.addAttribute("appointments", appointments);
////
////        return "trainer/notes";
////    }
////
////    @PostMapping("/{appointmentId}")
////    public String saveNotes(
////            @PathVariable Long appointmentId,
////            @RequestParam String notes) {
////
////        sessionService.createSession(
////                appointmentId,
////                currentUser.requireTrainerId(),
////                notes
////        );
////
////        return "redirect:/trainer/notes";
////    }
////}
