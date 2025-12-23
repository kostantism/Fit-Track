package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.AppointmentStatus;
import gr.hua.dit.fittrack.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Έλεγχος overlapping appointments για trainer
     *
     * Overlap logic:
     * existing.start < newEnd AND existing.end > newStart
     */
    boolean existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            Person trainer,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime
    );

    /**
     * Μέτρηση ενεργών (approved) appointments ανά customer
     */
    long countByCustomerAndStatus(Person customer, AppointmentStatus status);

    long countByCustomerAndStatusIn(Person customer, List<AppointmentStatus> statuses);

    /**
     * Όλα τα appointments ενός trainer
     */
    List<Appointment> findByTrainer(Person trainer);

    /**
     * Όλα τα appointments ενός customer
     */
    List<Appointment> findByCustomer(Person customer);
}
