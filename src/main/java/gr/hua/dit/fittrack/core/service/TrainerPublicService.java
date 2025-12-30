package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.model.TrainerProfile;
import gr.hua.dit.fittrack.core.port.impl.dto.TrainerPublicView;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.TrainerProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TrainerPublicService {

    private final PersonRepository personRepository;
    private final TrainerProfileRepository trainerProfileRepository;

    public TrainerPublicService(
            PersonRepository personRepository,
            TrainerProfileRepository trainerProfileRepository
    ) {
        this.personRepository = personRepository;
        this.trainerProfileRepository = trainerProfileRepository;
    }

    /**
     * Public listing of trainers (for guests)
     */
    public List<TrainerPublicView> listPublicTrainers() {

        // 🔹 μόνο TRAINER users
        List<Person> trainers = personRepository.findByType(PersonType.TRAINER);

        return trainers.stream()
                .map(this::toPublicView)
                .toList();
    }

    private TrainerPublicView toPublicView(Person trainer) {

        TrainerProfile profile = trainerProfileRepository
                .findByTrainer(trainer)
                .orElse(null);

        return new TrainerPublicView(
                trainer.getId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                profile != null ? profile.getSpecialization() : null,
                profile != null ? profile.getArea() : null,
                profile != null ? profile.getBio() : null
        );
    }
}
