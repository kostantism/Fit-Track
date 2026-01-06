package gr.hua.dit.fittrack.core.service.mapper;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.service.model.PersonView;
import org.springframework.stereotype.Component;

/**
 * Mapper to convert {@link Person} to {@link PersonView}.
 */
@Component
public class PersonMapper {

    public PersonView convertPersonToPersonView(final Person person) {
        if (person == null) {
            return null;
        }

        final PersonView personView = new PersonView(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getMobilePhoneNumber(),
                person.getEmailAddress(),
                person.getType()
        );

        return personView;
    }
}
