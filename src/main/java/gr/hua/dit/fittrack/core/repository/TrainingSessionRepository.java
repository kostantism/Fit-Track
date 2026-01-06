package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.TrainingSession;
import gr.hua.dit.fittrack.core.model.TrainingSessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    List<TrainingSession> findByTrainer(Person trainer);

    List<TrainingSession> findByCustomer(Person customer);

    Optional<TrainingSession> findByAppointmentId(Long appointmentId);

    boolean existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThan(
            Person trainer,
            LocalDateTime endTime,
            LocalDateTime startTime
    );

    long countByCustomerAndStatus(Person customer, TrainingSessionStatus status);
}
