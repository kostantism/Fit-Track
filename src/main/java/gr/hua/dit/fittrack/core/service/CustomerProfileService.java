package gr.hua.dit.fittrack.core.service;

import gr.hua.dit.fittrack.core.model.CustomerProfile;
import gr.hua.dit.fittrack.core.model.FitnessGoal;
import gr.hua.dit.fittrack.core.model.Person;

public interface CustomerProfileService {

    CustomerProfile getOrCreateProfile(Person customer);

    CustomerProfile updateProfile(
            Person customer,
            FitnessGoal goal,
            String notes
    );
}

