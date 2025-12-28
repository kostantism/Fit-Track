package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.service.model.CreateProgressEntryRequest;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;

import java.util.List;

public interface ProgressService {

    ProgressEntryView createProgressEntry(
            Long customerId,
            CreateProgressEntryRequest request
    );

    List<ProgressEntryView> getProgressForCustomer(Long customerId);
}
