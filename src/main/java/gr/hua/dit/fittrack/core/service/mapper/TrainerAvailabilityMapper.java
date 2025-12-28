package gr.hua.dit.fittrack.core.service.mapper;

import gr.hua.dit.fittrack.core.model.TrainerAvailability;
import gr.hua.dit.fittrack.core.service.model.AvailabilityView;
import org.springframework.stereotype.Component;

@Component
public class TrainerAvailabilityMapper {

    public AvailabilityView toView(TrainerAvailability availability) {
        return new AvailabilityView(
                availability.getId(),
                availability.getTrainer().getId(),
                availability.getStartTime(),
                availability.getEndTime(),
                availability.getStatus()
        );
    }
}
