package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.AppointmentStatus;
import gr.hua.dit.fittrack.core.port.impl.dto.AppointmentDTO;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final PersonRepository personRepository;
    private final CurrentUserProvider currentUserProvider;

    public AppointmentController(
            AppointmentService appointmentService,
            PersonRepository personRepository,
            CurrentUserProvider currentUserProvider
    ) {
        this.appointmentService = appointmentService;
        this.personRepository = personRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @PostMapping
    public AppointmentDTO createAppointment(
            @RequestParam long trainerId,
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end
    ) {
        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Appointment appointment = appointmentService.createAppointment(customer, trainer, start, end);

        return new AppointmentDTO(
                appointment.getId(),
                customer.getId(),
                trainer.getId(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus().name()
        );
    }

    @GetMapping("/my")
    public List<AppointmentDTO> getMyAppointments() {
        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return appointmentService.getAppointmentsForCustomer(customer).stream()
                .map(a -> new AppointmentDTO(
                        a.getId(),
                        customer.getId(),
                        a.getTrainer().getId(),
                        a.getStartDateTime(),
                        a.getEndDateTime(),
                        a.getStatus().name()
                ))
                .collect(Collectors.toList());
    }
}
