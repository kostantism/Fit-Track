package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    List<Person> findByMobilePhoneNumberAndEmailAddress(String mobilePhoneNumber, String emailAddress);

    List<Person> findByFirstNameAndLastName(String firstName, String lastName);

}

