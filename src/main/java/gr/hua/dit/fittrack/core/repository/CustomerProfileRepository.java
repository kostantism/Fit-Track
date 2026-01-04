package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.CustomerProfile;
import gr.hua.dit.fittrack.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerProfileRepository
        extends JpaRepository<CustomerProfile, Long> {

    Optional<CustomerProfile> findByCustomer(Person customer);
}
