package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.TrainerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gr.hua.dit.fittrack.core.model.Person;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerProfileRepository
        extends JpaRepository<TrainerProfile, Long> {

    List<TrainerProfile> findAll();

    List<TrainerProfile> findByAreaIgnoreCase(String area);

    List<TrainerProfile> findBySpecializationIgnoreCase(String specialization);

    List<TrainerProfile> findByAreaIgnoreCaseAndSpecializationIgnoreCase(
            String area,
            String specialization
    );

    Optional<TrainerProfile> findByTrainer(Person trainer);
}
