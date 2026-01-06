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

    boolean existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThan(
            Person trainer,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime
    );

    boolean existsByTrainerAndStartDateTimeLessThanAndEndDateTimeGreaterThanAndStatusIn(
            Person trainer,
            LocalDateTime endDateTime,
            LocalDateTime startDateTime,
            List<AppointmentStatus> statuses
    );

    long countByCustomerAndStatusIn(Person customer, List<AppointmentStatus> statuses);


    long countByCustomerAndStatus(Person customer, AppointmentStatus status);

    List<Appointment> findByTrainer(Person trainer);

    List<Appointment> findByCustomer(Person customer);

    Optional<Appointment> findById(Long id);

    List<Appointment> findByTrainerAndStatus(Person trainer, AppointmentStatus status);

}

