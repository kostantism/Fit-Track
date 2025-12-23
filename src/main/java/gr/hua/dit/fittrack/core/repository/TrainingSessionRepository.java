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

    // 🔍 Όλα τα sessions ενός trainer
    List<TrainingSession> findByTrainer(Person trainer);

    // 🔍 Όλα τα sessions ενός customer
    List<TrainingSession> findByCustomer(Person customer);

    // 🔍 Session που αντιστοιχεί σε συγκεκριμένο appointment
    Optional<TrainingSession> findByAppointmentId(Long appointmentId);

    // ❌ Overlapping sessions για trainer (ασφάλεια)
    boolean existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThan(
            Person trainer,
            LocalDateTime endTime,
            LocalDateTime startTime
    );

    // 📊 Active / planned sessions ενός customer
    long countByCustomerAndStatus(Person customer, TrainingSessionStatus status);
}
