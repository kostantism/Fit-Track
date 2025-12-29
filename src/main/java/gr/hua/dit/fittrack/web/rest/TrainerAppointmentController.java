package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.AppointmentStatus;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.port.impl.dto.AppointmentDTO;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.model.CreateAppointmentRequest;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class TrainerAppointmentController {

    private final AppointmentService appointmentService;
    private final PersonRepository personRepository;
    private final CurrentUserProvider currentUserProvider;

    public TrainerAppointmentController(AppointmentService appointmentService,
                                        PersonRepository personRepository,
                                        CurrentUserProvider currentUserProvider) {
        this.appointmentService = appointmentService;
        this.personRepository = personRepository;
        this.currentUserProvider = currentUserProvider;
    }

    // ---------------------------
    // Create Appointment (Customer)
    // ---------------------------
    @PostMapping
    public AppointmentDTO createAppointment(@RequestBody @Valid CreateAppointmentRequest request) {
        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Person trainer = personRepository.findById(request.trainerId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Appointment appointment = appointmentService.createAppointment(
                customer,
                trainer,
                request.startDateTime(),
                request.endDateTime()
        );

        return toDTO(appointment);
    }

    // ---------------------------
    // Approve Appointment (Trainer)
    // ---------------------------
    @PostMapping("/{id}/approve")
    public AppointmentDTO approveAppointment(@PathVariable Long id) {
        long trainerId = currentUserProvider.requireTrainerId();
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Appointment appointment = appointmentService.getAppointmentById(id);
        Appointment approved = appointmentService.approveAppointment(appointment, trainer);

        return toDTO(approved);
    }

    // ---------------------------
    // Reject Appointment (Trainer)
    // ---------------------------
    @PostMapping("/{id}/reject")
    public AppointmentDTO rejectAppointment(@PathVariable Long id) {
        long trainerId = currentUserProvider.requireTrainerId();
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        Appointment appointment = appointmentService.getAppointmentById(id);

        // Έλεγχος ιδιοκτησίας
        if (!appointment.getTrainer().equals(trainer)) {
            throw new IllegalStateException("Trainer does not own this appointment");
        }

        Appointment rejected = appointmentService.rejectAppointment(appointment);
        return toDTO(rejected);
    }

    // ---------------------------
    // Cancel Appointment (Customer)
    // ---------------------------
    @PostMapping("/{id}/cancel")
    public AppointmentDTO cancelAppointment(@PathVariable Long id) {
        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        Appointment appointment = appointmentService.getAppointmentById(id);
        Appointment cancelled = appointmentService.cancelByCustomer(appointment, customer);

        return toDTO(cancelled);
    }

    // ---------------------------
    // Get Appointments for Customer
    // ---------------------------
    @GetMapping("/my")
    public List<AppointmentDTO> getMyAppointments() {
        long customerId = currentUserProvider.requireCustomerId();
        Person customer = personRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        return appointmentService.getAppointmentsForCustomer(customer)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------------------------
    // Get Appointments for Trainer
    // ---------------------------
    @GetMapping("/trainer")
    public List<AppointmentDTO> getTrainerAppointments() {
        long trainerId = currentUserProvider.requireTrainerId();
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        return appointmentService.getAppointmentsForTrainer(trainer)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ---------------------------
    // Helper: Convert entity to DTO
    // ---------------------------
    private AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getTrainer().getId(),
                appointment.getCustomer().getId(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus().name() // <-- κάνουμε .name() εδώ
        );
    }

}
