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

    private static final int MAX_ACTIVE_APPOINTMENTS_PER_USER = 5; // παράδειγμα, αλλά μπορεί να αλλάξει

    public AppointmentService(AppointmentRepository appointmentRepository,
                              TrainerAvailabilityRepository availabilityRepository) {
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
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

        // ❌ Ραντεβού στο παρελθόν
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
        boolean overlap = appointmentRepository.existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThan(
                trainer, end, start
        );
        if (overlap) {
            throw new IllegalStateException("Trainer has overlapping appointment");
        }

        // ❌ Έλεγχος διαθέσιμων slots του trainer
        boolean available = availabilityRepository.existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
                trainer,
                start.toLocalDate(),
                start.toLocalTime(),
                end.toLocalTime(),
                AvailabilityStatus.AVAILABLE
        );
        if (!available) {
            throw new IllegalStateException("Trainer is not available at this time");
        }

        Appointment appointment = new Appointment(
                customer,
                trainer,
                start,
                end,
                AppointmentStatus.PENDING
        );

        return appointmentRepository.save(appointment);
    }

    /**
     * Έγκριση ραντεβού από trainer
     */
    public Appointment approveAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.APPROVED);
        return appointmentRepository.save(appointment);
    }

    /**
     * Απόρριψη ραντεβού από trainer
     */
    public Appointment rejectAppointment(Appointment appointment) {
        appointment.setStatus(AppointmentStatus.REJECTED);
        return appointmentRepository.save(appointment);
    }

    /**
     * Ανάκτηση ραντεβού ενός χρήστη
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
}
