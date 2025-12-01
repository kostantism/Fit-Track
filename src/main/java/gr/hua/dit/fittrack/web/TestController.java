package gr.hua.dit.fittrack.web;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.model.PersonType;
import gr.hua.dit.fittrack.core.repository.PersonRepository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for testing.
 */
@RestController
public class TestController {

    private final PersonRepository personRepository;

    public TestController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(value = "/test", produces = MediaType.TEXT_PLAIN_VALUE)
    public String test() {
        for (int i=0; i<100; i++) {
            Person person1 = new Person();
            person1.setId(null);
            person1.setFirstName("K");
            person1.setLastName("M");
            person1.setEmailAddress("KM@gmail.com");
            person1.setMobilePhoneNumber("+306900000000");
            person1.setType(PersonType.CUSTOMER);
            person1.setPasswordHash("<hash>");

            person1 = this.personRepository.save(person1);
        }

        final var people = this.personRepository
                .findByFirstNameAndLastName("K", "M");

        final String stringToServe = String.join(
                "\n", people.stream().map(Person::toString).toList());

        return stringToServe;
    }
}
