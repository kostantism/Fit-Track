package gr.hua.dit.fittrack.web.rest;


import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.port.impl.dto.AppointmentDTO;
import gr.hua.dit.fittrack.core.security.CurrentUserProvider;
import gr.hua.dit.fittrack.core.service.AppointmentService;
import gr.hua.dit.fittrack.core.service.model.CreateAppointmentRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class TrainerAppointmentController {

    private final AppointmentService appointmentService;
    private final CurrentUserProvider currentUserProvider;

    public TrainerAppointmentController(
            AppointmentService appointmentService,
            CurrentUserProvider currentUserProvider
    ) {
        this.appointmentService = appointmentService;
        this.currentUserProvider = currentUserProvider;
    }

    @PostMapping
    public AppointmentDTO createAppointment(
            @RequestBody @Valid CreateAppointmentRequest request
    ) {
        long customerId = currentUserProvider.requireCustomerId();

        Appointment appointment = appointmentService.createAppointment(
                customerId,
                request.trainerId(),
                request.startDateTime(),
                request.endDateTime()
        );

        return toDTO(appointment);
    }


    @PostMapping("/{id}/approve")
    public void approveAppointment(@PathVariable Long id) {
        long trainerId = currentUserProvider.requireTrainerId();
        appointmentService.approveAppointment(id, trainerId);
    }

    @PostMapping("/{id}/reject")
    public AppointmentDTO rejectAppointment(@PathVariable Long id) {

        long trainerId = currentUserProvider.requireTrainerId();

        appointmentService.rejectAppointment(id, trainerId);

        Appointment appointment = appointmentService.getAppointmentById(id);
        return toDTO(appointment);
    }

    @PostMapping("/{id}/cancel")
    public void cancelAppointment(@PathVariable Long id) {
        long customerId = currentUserProvider.requireCustomerId();
        appointmentService.cancelAppointment(id, customerId);
    }

    @GetMapping("/my")
    public List<AppointmentDTO> getMyAppointments() {
        long customerId = currentUserProvider.requireCustomerId();

        return appointmentService.getAppointmentsForCustomer(customerId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/trainer")
    public List<AppointmentDTO> getTrainerAppointments() {
        long trainerId = currentUserProvider.requireTrainerId();

        return appointmentService.getAppointmentsForTrainer(trainerId)
                .stream()
                .map(this::toDTO)
                .toList();
    }

    private AppointmentDTO toDTO(Appointment appointment) {
        return new AppointmentDTO(
                appointment.getId(),
                appointment.getTrainer().getId(),
                appointment.getCustomer().getId(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus().name()
        );
    }
}

