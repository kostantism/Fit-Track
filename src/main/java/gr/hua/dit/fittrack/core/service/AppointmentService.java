package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(
            Long customerId,
            Long trainerId,
            LocalDateTime start,
            LocalDateTime end
    );

    void approveAppointment(Long appointmentId, Long trainerId);

    void rejectAppointment(Long appointmentId, Long trainerId);

    List<Appointment> getAppointmentsForTrainer(Long trainerId);

    List<Appointment> getApprovedAppointmentsForTrainer(Long trainerId);

    void cancelAppointment(Long appointmentId, Long customerId);

    List<Appointment> getAppointmentsForCustomer(Long customerId);

    Appointment getAppointmentById(Long id);

    void deleteAppointment(Long id);
}


