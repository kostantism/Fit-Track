package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.Person;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(
            Long customerId,
            Long trainerId,
            LocalDateTime start,
            LocalDateTime end
    );

    Appointment approveAppointment(Appointment appointment, Person trainer);

    Appointment cancelByCustomer(Appointment appointment, Person customer);

    Appointment rejectAppointment(Appointment appointment);

    List<Appointment> getAppointmentsForCustomer(Person customer);

    List<Appointment> getAppointmentsForTrainer(Person trainer);

    Appointment getAppointmentById(Long id);

    void deleteAppointment(Long id);

    List<Appointment> getApprovedAppointmentsForTrainer(Long trainerId);

    List<Appointment> getAppointmentsForTrainer(Long trainerId);

    void approveAppointment(Long appointmentId, Long trainerId);

    void  rejectAppointment(Long appointmentId);

    void rejectAppointment(Long appointmentId, Long trainerId);

    void cancelByCustomer(Long appointmentId, Long customerId);

    List<Appointment> getAppointmentsForCustomer(Long customerId);

    void cancelAppointment(Long appointmentId, Long customerId);


}


