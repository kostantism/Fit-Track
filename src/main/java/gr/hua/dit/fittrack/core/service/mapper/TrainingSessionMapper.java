package gr.hua.dit.fittrack.core.service.mapper;

import gr.hua.dit.fittrack.core.model.TrainingSession;
import gr.hua.dit.fittrack.core.service.model.TrainingSessionView;
import org.springframework.stereotype.Component;

@Component
public class TrainingSessionMapper {

    public TrainingSessionView toView(TrainingSession session) {
        return new TrainingSessionView(
                session.getId(),
                session.getAppointment().getId(),
                session.getTrainer().getId(),
                session.getCustomer().getId(),
                session.getStartTime(),
                session.getEndTime(),
                session.getStatus(),
                session.getNotes(),
                session.getCreatedAt()
        );
    }
}
