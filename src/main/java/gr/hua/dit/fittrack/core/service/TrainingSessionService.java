package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.TrainingSession;
import gr.hua.dit.fittrack.core.model.TrainingSessionStatus;
import gr.hua.dit.fittrack.core.repository.TrainingSessionRepository;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TrainingSessionService {

    private final TrainingSessionRepository sessionRepository;
    private final PersonRepository personRepository;

    public TrainingSessionService(TrainingSessionRepository sessionRepository,
                                  PersonRepository personRepository) {
        this.sessionRepository = sessionRepository;
        this.personRepository = personRepository;
    }

    // 🔹 Επιστρέφει όλα τα sessions
    @Transactional(readOnly = true)
    public List<TrainingSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    // 🔹 Επιστρέφει ένα session με id
    @Transactional(readOnly = true)
    public TrainingSession getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with id: " + id));
    }

    // 🔹 Δημιουργεί νέο session
    public TrainingSession createSession(Long trainerId, String notes, LocalDateTime start, LocalDateTime end) {
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found with id: " + trainerId));

        TrainingSession session = new TrainingSession();
        session.setTrainer(trainer);
        session.setNotes(notes);
        session.setStartTime(start);
        session.setEndTime(end);
        session.setStatus(TrainingSessionStatus.PLANNED);

        return sessionRepository.save(session);
    }

    // 🔹 Διαγράφει session
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
