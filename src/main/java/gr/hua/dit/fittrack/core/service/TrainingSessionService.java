package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.*;
import gr.hua.dit.fittrack.core.repository.TrainingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;

    public TrainingSessionService(TrainingSessionRepository trainingSessionRepository) {
        this.trainingSessionRepository = trainingSessionRepository;
    }

    /**
     * Δημιουργία TrainingSession μετά από εγκεκριμένο appointment
     */
    public TrainingSession createSession(Appointment appointment) {

        // ❌ Appointment must be approved
        if (appointment.getStatus() != AppointmentStatus.APPROVED) {
            throw new IllegalStateException("Training session can be created only for approved appointments");
        }

        // ❌ Appointment must be in the future or now
        if (appointment.getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Cannot create training session for past appointment");
        }

        Person trainer = appointment.getTrainer();
        Person customer = appointment.getCustomer();

        // ❌ Role safety
        if (trainer.getType() != PersonType.TRAINER) {
            throw new IllegalArgumentException("Trainer must have TRAINER role");
        }

        if (customer.getType() != PersonType.CUSTOMER) {
            throw new IllegalArgumentException("Customer must have CUSTOMER role");
        }

        // ❌ Overlapping training sessions for trainer
        boolean overlap = trainingSessionRepository
                .existsByTrainerAndStartTimeLessThanAndEndTimeGreaterThan(
                        trainer,
                        appointment.getEndDateTime(),
                        appointment.getStartDateTime()
                );

        if (overlap) {
            throw new IllegalStateException("Trainer already has a training session at this time");
        }

        // ❌ Only one session per appointment
        trainingSessionRepository.findByAppointmentId(appointment.getId())
                .ifPresent(s -> {
                    throw new IllegalStateException("Training session already exists for this appointment");
                });

        TrainingSession session = new TrainingSession(
                appointment,
                trainer,
                customer,
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                TrainingSessionStatus.PLANNED
        );

        return trainingSessionRepository.save(session);
    }

    /**
     * Ολοκλήρωση training session
     */
    public TrainingSession completeSession(Long sessionId, String notes) {

        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Training session not found"));

        if (session.getStatus() != TrainingSessionStatus.PLANNED) {
            throw new IllegalStateException("Only planned sessions can be completed");
        }

        session.setStatus(TrainingSessionStatus.COMPLETED);
        session.setNotes(notes);

        return trainingSessionRepository.save(session);
    }

    /**
     * Ακύρωση training session
     */
    public TrainingSession cancelSession(Long sessionId) {

        TrainingSession session = trainingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Training session not found"));

        if (session.getStatus() == TrainingSessionStatus.COMPLETED) {
            throw new IllegalStateException("Completed sessions cannot be cancelled");
        }

        session.setStatus(TrainingSessionStatus.CANCELLED);
        return trainingSessionRepository.save(session);
    }

    /**
     * Προβολή sessions
     */
    @Transactional(readOnly = true)
    public List<TrainingSession> getSessionsForTrainer(Person trainer) {
        return trainingSessionRepository.findByTrainer(trainer);
    }

    @Transactional(readOnly = true)
    public List<TrainingSession> getSessionsForCustomer(Person customer) {
        return trainingSessionRepository.findByCustomer(customer);
    }
}
