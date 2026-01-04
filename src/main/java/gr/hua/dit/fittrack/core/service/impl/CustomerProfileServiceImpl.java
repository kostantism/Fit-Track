package gr.hua.dit.fittrack.core.service.impl;

import gr.hua.dit.fittrack.core.model.CustomerProfile;
import gr.hua.dit.fittrack.core.model.FitnessGoal;
import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.repository.CustomerProfileRepository;
import gr.hua.dit.fittrack.core.service.CustomerProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerProfileServiceImpl
        implements CustomerProfileService {

    private final CustomerProfileRepository repository;

    public CustomerProfileServiceImpl(CustomerProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public CustomerProfile getOrCreateProfile(Person customer) {

        if (customer.getType() != PersonType.CUSTOMER) {
            throw new SecurityException("Only customers have profiles");
        }

        return repository.findByCustomer(customer)
                .orElseGet(() -> {
                    CustomerProfile profile = new CustomerProfile();
                    profile.setCustomer(customer);
                    profile.setGoal(FitnessGoal.GENERAL_FITNESS);
                    return repository.save(profile);
                });
    }

    @Override
    public CustomerProfile updateProfile(
            Person customer,
            FitnessGoal goal,
            String notes
    ) {
        CustomerProfile profile = getOrCreateProfile(customer);
        profile.setGoal(goal);
        profile.setNotes(notes);
        return repository.save(profile);
    }
}

