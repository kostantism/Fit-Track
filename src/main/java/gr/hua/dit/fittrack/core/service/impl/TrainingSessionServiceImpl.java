package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.AppointmentRepository;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.TrainingSessionRepository;
import gr.hua.dit.fittrack.core.service.TrainingSessionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrainingSessionServiceImpl implements TrainingSessionService {

    private final TrainingSessionRepository sessionRepository;
    private final AppointmentRepository appointmentRepository;
    private final PersonRepository personRepository;

    public TrainingSessionServiceImpl(
            TrainingSessionRepository sessionRepository,
            AppointmentRepository appointmentRepository,
            PersonRepository personRepository
    ) {
        this.sessionRepository = sessionRepository;
        this.appointmentRepository = appointmentRepository;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingSession> getAllSessions() {
        return sessionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingSession getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Session not found with id: " + id));
    }

    @Override
    public TrainingSession createSession(Long appointmentId, Long trainerId, String notes, String trainingPlan) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        Person trainer = personRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Only trainer can create a session");
        }

        if (!appointment.getTrainer().equals(trainer)) {
            throw new IllegalStateException("Trainer does not own appointment");
        }

        if (appointment.getStatus() != AppointmentStatus.APPROVED) {
            throw new IllegalStateException("Session allowed only for APPROVED appointment");
        }

        if (sessionRepository.findByAppointmentId(appointmentId).isPresent()) {
            throw new IllegalStateException("Session already exists for this appointment");
        }

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

    @Override
    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}
