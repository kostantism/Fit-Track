package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByMobilePhoneNumberAndEmailAddress(String mobilePhoneNumber, String emailAddress);

    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmailAddressIgnoreCase(final String emailAddress);

    boolean existsByMobilePhoneNumber(final String mobilePhoneNumber);

    Locale findByEmailAddressIgnoreCase(String username);
}

