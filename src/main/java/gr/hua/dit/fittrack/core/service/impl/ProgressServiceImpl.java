package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.ProgressEntry;
import gr.hua.dit.fittrack.core.repository.PersonRepository;
import gr.hua.dit.fittrack.core.repository.ProgressEntryRepository;
import gr.hua.dit.fittrack.core.service.ProgressService;
import gr.hua.dit.fittrack.core.service.mapper.ProgressEntryMapper;
import gr.hua.dit.fittrack.core.service.model.CreateProgressEntryRequest;
import gr.hua.dit.fittrack.core.service.model.ProgressEntryView;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;


import java.util.List;

@Service
@Transactional
public class ProgressServiceImpl implements ProgressService {

    private final ProgressEntryRepository progressRepo;
    private final PersonRepository personRepo;
    private final ProgressEntryMapper mapper;

    public ProgressServiceImpl(
            ProgressEntryRepository progressRepo,
            PersonRepository personRepo,
            ProgressEntryMapper mapper
    ) {
        this.progressRepo = progressRepo;
        this.personRepo = personRepo;
        this.mapper = mapper;
    }

    @Override
    public ProgressEntryView createProgressEntry(
            Long customerId,
            CreateProgressEntryRequest request
    ) {
        Person customer = personRepo.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (request.weightKg() == null && request.runTimeSeconds() == null) {
            throw new IllegalArgumentException("At least one metric must be provided");
        }

        if (request.entryDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot create progress entry for a future date");
        }


        ProgressEntry entry = new ProgressEntry(
                customer,
                request.entryDate(),
                request.weightKg(),
                request.runTimeSeconds(),
                request.notes()
        );

        return mapper.toView(progressRepo.save(entry));
    }

    @Override
    public List<ProgressEntryView> getProgressForCustomer(Long customerId) {
        return progressRepo.findByCustomerIdOrderByEntryDateDesc(customerId)
                .stream()
                .map(mapper::toView)
                .toList();
    }
}
