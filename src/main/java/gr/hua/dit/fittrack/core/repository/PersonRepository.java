package gr.hua.dit.fittrack.core.repository;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByMobilePhoneNumberAndEmailAddress(
            String mobilePhoneNumber,
            String emailAddress
    );

    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);

    boolean existsByEmailAddressIgnoreCase(String emailAddress);

    boolean existsByMobilePhoneNumber(String mobilePhoneNumber);

    Optional<Person> findByEmailAddressIgnoreCase(String emailAddress);

    //  ΝΕΟ
    List<Person> findByType(PersonType type);
}

//package gr.hua.dit.fittrack.core.repository;
//
//import gr.hua.dit.fittrack.core.model.Person;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface PersonRepository extends JpaRepository<Person, Long> {
//
//    Optional<Person> findByMobilePhoneNumberAndEmailAddress(String mobilePhoneNumber, String emailAddress);
//
//    Optional<Person> findByFirstNameAndLastName(String firstName, String lastName);
//
//    boolean existsByEmailAddressIgnoreCase(final String emailAddress);
//
//    boolean existsByMobilePhoneNumber(final String mobilePhoneNumber);
//
//    Optional<Person> findByEmailAddressIgnoreCase(final String emailAddress);}
//
