package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.TrainingSession;

import java.util.List;

public interface TrainingSessionService {

    List<TrainingSession> getAllSessions();

    TrainingSession getSessionById(Long id);

    TrainingSession createSession(Long appointmentId, Long trainerId, String notes);

    void deleteSession(Long id);
}
