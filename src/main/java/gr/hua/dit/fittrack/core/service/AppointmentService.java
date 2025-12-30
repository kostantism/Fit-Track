package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
import gr.hua.dit.fittrack.core.repository.TrainerAvailabilityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TrainerAvailabilityRepository availabilityRepository;
    private final NotificationService notificationService;

    private static final int MAX_ACTIVE_APPOINTMENTS_PER_USER = 5; // προσαρμόζεται ανά ανάγκη

    public AppointmentService(AppointmentRepository appointmentRepository,
                              TrainerAvailabilityRepository availabilityRepository,
                              NotificationService notificationService) {
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
        this.notificationService = notificationService;
    }

    /**
     * Δημιουργία νέου ραντεβού
     */
    public Appointment createAppointment(Person customer, Person trainer,
                                         LocalDateTime start, LocalDateTime end) {

        // ❌ Έλεγχος τύπων χρηστών
        if (customer.getType() != PersonType.CUSTOMER) {
            throw new IllegalArgumentException("Only customers can book appointments");
        }
        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Appointment must be with a trainer");
        }

        // ❌ Ραντεβού στο παρελθόν / invalid range
        if (start.isBefore(LocalDateTime.now()) || !start.isBefore(end)) {
            throw new IllegalArgumentException("Invalid appointment time");
        }

        // ❌ Μέγιστος αριθμός ενεργών ραντεβού
        long activeCount = appointmentRepository.countByCustomerAndStatusIn(
                customer,
                List.of(AppointmentStatus.PENDING, AppointmentStatus.APPROVED)
        );
        if (activeCount >= MAX_ACTIVE_APPOINTMENTS_PER_USER) {
            throw new IllegalStateException("Customer has reached max active appointments");
        }

        // ❌ Overlapping ραντεβού για trainer
        boolean overlap = appointmentRepository
                .existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
                        trainer, end, start
                );
        if (overlap) {
            throw new IllegalStateException("Trainer has overlapping appointment");
        }

        // ❌ Έλεγχος διαθέσιμων slots του trainer
        boolean available = availabilityRepository
                .existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
                        trainer,
                        end,
                        start,
                        AvailabilityStatus.AVAILABLE
                );
        if (!available) {
            throw new IllegalStateException("Trainer is not available at this time");
        }

        Appointment appointment = new Appointment(
                trainer,
                customer,
                start,
                end,
                AppointmentStatus.PENDING
        );

        return appointmentRepository.save(appointment);
    }

    /**
     * Έγκριση ραντεβού από trainer
     */
    public Appointment approveAppointment(Appointment appointment, Person trainer) {

        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Only trainer can approve appointment");
        }

        if (!appointment.getTrainer().equals(trainer)) {
            throw new IllegalStateException("Trainer does not own this appointment");
        }

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new IllegalStateException(
                    "Only PENDING appointments can be approved"
            );
        }

        appointment.setStatus(AppointmentStatus.APPROVED);
        Appointment saved = appointmentRepository.save(appointment);

        notificationService.notifyAppointmentApproved(saved);

        return saved;
    }

    //Απόρριψη ραντεβού απο customer
    public Appointment cancelByCustomer(Appointment appointment, Person customer) {
        if (customer.getType() != PersonType.CUSTOMER) {
            throw new IllegalArgumentException("Only customer can cancel");
        }

        if (!appointment.getCustomer().equals(customer)) {
            throw new IllegalStateException("Not your appointment");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED ||
                appointment.getStatus() == AppointmentStatus.REJECTED) {
            throw new IllegalStateException("Appointment cannot be cancelled");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        Appointment saved = appointmentRepository.save(appointment);

        notificationService.notifyAppointmentCancelled(saved);

        return saved;
    }


    /**
     * Απόρριψη ραντεβού από trainer
     */
    public Appointment rejectAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.REJECTED);
        return appointmentRepository.save(appointment);
    }

    /**
     * Ανάκτηση ραντεβού ενός χρήστη (customer)
     */
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForCustomer(Person customer) {
        return appointmentRepository.findByCustomer(customer);
    }

    /**
     * Ανάκτηση ραντεβού ενός trainer
     */
    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentsForTrainer(Person trainer) {
        return appointmentRepository.findByTrainer(trainer);
    }
    /**
     * Ανάκτηση ραντεβού με βάση το ID
     */
    @Transactional(readOnly = true)
    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
    }
    /**
     * Διαγραφή ραντεβού με βάση το ID
     */
    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with id: " + id));
        appointmentRepository.delete(appointment);
    }


}