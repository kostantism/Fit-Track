package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.AvailabilityStatus;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.TrainerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TrainerAvailabilityRepository
        extends JpaRepository<TrainerAvailability, Long> {


    List<TrainerAvailability> findByTrainerAndDate(Person trainer, LocalDate date);


    boolean existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
            Person trainer,
            LocalDateTime endTime,
            LocalDateTime startTime,
            AvailabilityStatus status
    );

    boolean existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Person trainer,
            LocalDate date,
            LocalDateTime endTime,
            LocalDateTime startTime
    );
}
