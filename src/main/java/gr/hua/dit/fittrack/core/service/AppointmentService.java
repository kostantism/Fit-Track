package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Appointment;

import java.time.LocalDateTime;
import java.util.List;

//public interface AppointmentService {
//
//    Appointment createAppointment(
//            Long customerId,
//            Long trainerId,
//            LocalDateTime start,
//            LocalDateTime end
//    );
//
////    Appointment approveAppointment(Appointment appointment, Person trainer);
//
////    Appointment cancelByCustomer(Appointment appointment, Person customer);
//
//    Appointment rejectAppointment(Appointment appointment);
//
////    List<Appointment> getAppointmentsForCustomer(Person customer);
//
////    List<Appointment> getAppointmentsForTrainer(Person trainer);
//
//    Appointment getAppointmentById(Long id);
//
//    void deleteAppointment(Long id);
//
//    List<Appointment> getApprovedAppointmentsForTrainer(Long trainerId);
//
//    List<Appointment> getAppointmentsForTrainer(Long trainerId);
//
//    void approveAppointment(Long appointmentId, Long trainerId);
//
//    void  rejectAppointment(Long appointmentId);
//
//    void rejectAppointment(Long appointmentId, Long trainerId);
//
//    void cancelByCustomer(Long appointmentId, Long customerId);
//
//    List<Appointment> getAppointmentsForCustomer(Long customerId);
//
//    void cancelAppointment(Long appointmentId, Long customerId);
//
//
//}

public interface AppointmentService {

    // =========================
    // Create
    // =========================
    Appointment createAppointment(
            Long customerId,
            Long trainerId,
            LocalDateTime start,
            LocalDateTime end
    );

    // =========================
    // Trainer actions
    // =========================
    void approveAppointment(Long appointmentId, Long trainerId);

    void rejectAppointment(Long appointmentId, Long trainerId);

    List<Appointment> getAppointmentsForTrainer(Long trainerId);

    List<Appointment> getApprovedAppointmentsForTrainer(Long trainerId);

    // =========================
    // Customer actions
    // =========================
    void cancelAppointment(Long appointmentId, Long customerId);

    List<Appointment> getAppointmentsForCustomer(Long customerId);

    // =========================
    // Generic
    // =========================
    Appointment getAppointmentById(Long id);

    void deleteAppointment(Long id);
}


