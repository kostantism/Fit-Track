package gr.hua.dit.fittrack.core.service.mapper;

import gr.hua.dit.fittrack.core.model.Appointment;
import gr.hua.dit.fittrack.core.service.model.AppointmentView;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    public AppointmentView toView(Appointment appointment) {
        return new AppointmentView(
                appointment.getId(),
                appointment.getTrainer().getId(),
                appointment.getCustomer().getId(),
                appointment.getStartDateTime(),
                appointment.getEndDateTime(),
                appointment.getStatus(),
                appointment.getCreatedAt()
        );
    }
}
