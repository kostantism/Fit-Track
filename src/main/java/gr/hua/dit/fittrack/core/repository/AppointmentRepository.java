package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.AppointmentStatus;
import gr.hua.dit.fittrack.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Έλεγχος overlapping appointments για trainer
     * Logic: existing.start < newEnd AND existing.end > newStart
     */
    boolean existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            Person trainer,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime
    );

    /**
     * Έλεγχος overlapping appointments για trainer μόνο με συγκεκριμένα status
     */
    boolean existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThanAndStatusIn(
            Person trainer,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime,
            List<AppointmentStatus> statuses
    );

    /**
     * Μέτρηση ενεργών appointments (PENDING + APPROVED) ανά customer
     */
    long countByCustomerAndStatusIn(Person customer, List<AppointmentStatus> statuses);

    /**
     * Μέτρηση appointments με συγκεκριμένο status
     */
    long countByCustomerAndStatus(Person customer, AppointmentStatus status);

    /**
     * Ανάκτηση όλων των appointments ενός trainer
     */
    List<Appointment> findByTrainer(Person trainer);

    /**
     * Ανάκτηση όλων των appointments ενός customer
     */
    List<Appointment> findByCustomer(Person customer);

    /**
     * Ανάκτηση appointment με βάση το ID
     */
    Optional<Appointment> findById(Long id);

    /**
     * * Ανάκτηση approved appointment
     * */
    List<Appointment> findByTrainerAndStatus(Person trainer, AppointmentStatus status);

}

