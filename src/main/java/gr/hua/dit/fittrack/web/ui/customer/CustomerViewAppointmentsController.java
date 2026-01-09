package gr.hua.dit.fittrack.web.ui.customer;

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
@RequestMapping("/customer/appointments")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerViewAppointmentsController {

    private final AppointmentService appointmentService;
    private final CurrentUserProvider currentUserProvider;

    public CustomerViewAppointmentsController(
            AppointmentService appointmentService,
            CurrentUserProvider currentUserProvider
    ) {
        this.appointmentService = appointmentService;
        this.currentUserProvider = currentUserProvider;
    }

    @GetMapping
    public String showAppointments(Model model) {

        long customerId = currentUserProvider.requireCustomerId();

        List<Appointment> appointments =
                appointmentService.getAppointmentsForCustomer(customerId);

        model.addAttribute("appointments", appointments);
        return "customer/appointments";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id) {

        long customerId = currentUserProvider.requireCustomerId();
        appointmentService.cancelAppointment(id, customerId);

        return "redirect:/customer/appointments";
    }
}
