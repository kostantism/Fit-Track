package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.AvailabilityStatus;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.TrainerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface TrainerAvailabilityRepository
        extends JpaRepository<TrainerAvailability, Long> {

    /**
     * Επιστρέφει όλες τις διαθεσιμότητες ενός trainer για συγκεκριμένη ημερομηνία
     */
    List<TrainerAvailability> findByTrainerAndDate(Person trainer, LocalDate date);


    boolean existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThanAndStatus(
            Person trainer,
            LocalDate date,
            LocalTime endTime,
            LocalTime startTime,
            AvailabilityStatus status
    );


    /**
     * Έλεγχος αν υπάρχει overlapping availability slot για trainer
     *
     * Overlap logic:
     * existing.start < newEnd AND existing.end > newStart
     */
    boolean existsByTrainerAndDateAndStartTimeLessThanAndEndTimeGreaterThan(
            Person trainer,
            LocalDate date,
            LocalTime endTime,
            LocalTime startTime
    );
}
