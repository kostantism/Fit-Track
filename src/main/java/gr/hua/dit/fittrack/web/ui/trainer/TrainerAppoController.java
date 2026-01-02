//package gr.hua.dit.fittrack.web.ui.trainer;
//
//import gr.hua.dit.fittrack.core.model.Person;
//import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
//import gr.hua.dit.fittrack.core.service.AppointmentService;
//import gr.hua.dit.fittrack.core.service.PersonDataService;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//@PreAuthorize("hasRole('TRAINER')")
//public class TrainerAppoController {
//
//    private final AppointmentService appointmentService;
//    private final PersonDataService personDataService;
//    private final CurrentUserProvider currentUserProvider;
//
//    public TrainerAppoController(
//            AppointmentService appointmentService,
//            PersonDataService personDataService,
//            CurrentUserProvider currentUserProvider
//    ) {
//        this.appointmentService = appointmentService;
//        this.personDataService = personDataService;
//        this.currentUserProvider = currentUserProvider;
//    }
//
//    @GetMapping("/trainer/appointments")
//    public String appointments(Model model) {
//
//        // 🔐 από security context
//        long trainerId = currentUserProvider.requireTrainerId();
//
//        // ✅ JPA entity (σωστό)
//        Person trainer = personDataService.findPersonEntityById(trainerId);
//
//        model.addAttribute(
//                "appointments",
//                appointmentService.getAppointmentsForTrainer(trainer)
//        );
//
//        return "trainer/appointments";
//    }
//}
//
////package gr.hua.dit.fittrack.web.ui.trainer;
////
////import gr.hua.dit.fittrack.core.model.Person;
////import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
////import gr.hua.dit.fittrack.core.service.AppointmentService;
////import gr.hua.dit.fittrack.core.service.PersonDataService;
////import org.springframework.security.access.prepost.PreAuthorize;
////import org.springframework.stereotype.Controller;
////import org.springframework.ui.Model;
////import org.springframework.web.bind.annotation.GetMapping;
////
////@Controller
////@PreAuthorize("hasRole('TRAINER')")
////public class TrainerAppoController {
////
////    private final AppointmentService appointmentService;
////    private final PersonDataService personDataService;
////    private final CurrentUserProvider currentUserProvider;
////
////    public TrainerAppoController(
////            AppointmentService appointmentService,
////            PersonDataService personDataService,
////            CurrentUserProvider currentUserProvider) {
////        this.appointmentService = appointmentService;
////        this.personDataService = personDataService;
////        this.currentUserProvider = currentUserProvider;
////    }
////
////    @GetMapping("/trainer/appointments")
////    public String appointments(Model model) {
////
////        long trainerId = currentUserProvider.requireTrainerId();
////
////        Person trainer = personDataService
////                .getPersonById(trainerId);
////
////        model.addAttribute(
////                "appointments",
////                appointmentService.getAppointmentsForTrainer(trainer)
////        );
////
////        return "trainer/appointments";
////    }
////}
