package gr.hua.dit.fittrack.web.ui.trainer;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/trainer/appointments")
@PreAuthorize("hasRole('TRAINER')")
public class TrainerAppoController {

    private final AppointmentService appointmentService;
    private final CurrentUserProvider currentUserProvider;

    public TrainerAppoController(
            AppointmentService appointmentService,
            CurrentUserProvider currentUserProvider
    ) {
        this.appointmentService = appointmentService;
        this.currentUserProvider = currentUserProvider;
    }

    // 📌 Προβολή ραντεβού trainer
    @GetMapping
    public String showAppointments(Model model) {

        long trainerId = currentUserProvider.requireTrainerId();

        List<Appointment> appointments =
                appointmentService.getAppointmentsForTrainer(trainerId);

        model.addAttribute("appointments", appointments);
        return "trainer/appointments";
    }

    // ✅ Approve
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {

        long trainerId = currentUserProvider.requireTrainerId();

        appointmentService.approveAppointment(id, trainerId);

        return "redirect:/trainer/appointments";
    }

    // ❌ Reject
//    @PostMapping("/{id}/reject")
//    public String reject(@PathVariable Long id) {
//
//        appointmentService.rejectAppointment(id);
//
//        return "redirect:/trainer/appointments";
//    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {

        long trainerId = currentUserProvider.requireTrainerId();

        appointmentService.rejectAppointment(id, trainerId);

        return "redirect:/trainer/appointments";
    }
}