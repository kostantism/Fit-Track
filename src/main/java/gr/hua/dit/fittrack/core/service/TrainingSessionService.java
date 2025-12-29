package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.model.AppointmentStatus;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.model.TrainingSession;
import gr.hua.dit.fittrack.core.model.TrainingSessionStatus;
import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TrainingSessionService {

    private final TrainingSessionRepository sessionRepository;
    private final AppointmentRepository appointmentRepository;
    private final PersonRepository personRepository;

    public TrainingSessionService(
            TrainingSessionRepository sessionRepository,
            AppointmentRepository appointmentRepository,
            PersonRepository personRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.appointmentRepository = appointmentRepository;
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

    // 🔹 Δημιουργεί νέο session βάσει appointment
    public TrainingSession createSession(Long appointmentId, Long trainerId, String notes) {

        // Βρες appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        // Βρες trainer
        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        // ❌ Μόνο trainer μπορεί να δημιουργήσει session
        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Only trainer can create a session");
        }

        // ❌ Ο trainer πρέπει να είναι ιδιοκτήτης του appointment
        if (!appointment.getTrainer().equals(trainer)) {
            throw new IllegalStateException("Trainer does not own appointment");
        }

        // ❌ Το appointment πρέπει να είναι APPROVED
        if (appointment.getStatus() != AppointmentStatus.APPROVED) {
            throw new IllegalStateException("Session allowed only for APPROVED appointment");
        }

        // ❌ Μόνο ένα session ανά appointment
        if (sessionRepository.findByAppointmentId(appointmentId).isPresent()) {
            throw new IllegalStateException("Session already exists for this appointment");
        }

        // Δημιουργία session
        TrainingSession session = new TrainingSession();
        session.setAppointment(appointment);
        session.setTrainer(trainer);
        session.setCustomer(appointment.getCustomer());
        session.setStartTime(appointment.getStartDateTime());
        session.setEndTime(appointment.getEndDateTime());
        session.setStatus(TrainingSessionStatus.PLANNED);
        session.setNotes(notes);

        return sessionRepository.save(session);
    }

    // 🔹 Διαγράφει session
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}



/*package gr.hua.dit.fittrack.core.service;

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
}*/
