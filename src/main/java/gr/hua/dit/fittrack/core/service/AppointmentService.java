package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.Person;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentService {

    Appointment createAppointment(Person customer, Person trainer, LocalDateTime start, LocalDateTime end);

    Appointment approveAppointment(Appointment appointment, Person trainer);

    Appointment cancelByCustomer(Appointment appointment, Person customer);

    Appointment rejectAppointment(Appointment appointment);

    List<Appointment> getAppointmentsForCustomer(Person customer);

    List<Appointment> getAppointmentsForTrainer(Person trainer);

    Appointment getAppointmentById(Long id);

    void deleteAppointment(Long id);

    List<Appointment> getApprovedAppointmentsForTrainer(Person trainer);
}


