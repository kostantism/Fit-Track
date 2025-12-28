package gr.hua.dit.fittrack.core.service.mapper;

import gr.hua.dit.fittrack.core.model.ProgressEntry;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;
import org.springframework.stereotype.Component;

@Component
public class ProgressEntryMapper {

    public ProgressEntryView toView(ProgressEntry entry) {
        return new ProgressEntryView(
                entry.getId(),
                entry.getCustomer().getId(),
                entry.getEntryDate(),
                entry.getWeightKg(),
                entry.getRunTimeSeconds(),
                entry.getNotes(),
                entry.getCreatedAt()
        );
    }
}
