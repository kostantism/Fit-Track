package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import gr.hua.dit.fittrack.core.model.PersonType;


import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByMobilePhoneNumberAndEmailAddress(String mobilePhoneNumber, String emailAddress);

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmailAddressIgnoreCase(final String emailAddress);

    boolean existsByMobilePhoneNumber(final String mobilePhoneNumber);

    List<Person> findByType(PersonType type);

    Optional<Person> findByEmailAddressIgnoreCase(final String emailAddress);}

