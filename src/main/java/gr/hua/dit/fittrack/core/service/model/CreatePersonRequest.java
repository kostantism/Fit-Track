package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.PersonType;

/**
 * DTO for requesting the creation (registration) of a Person.
 */
public record CreatePersonRequest(
        PersonType type,
        String firstName,
        String lastName,
        String emailAddress,
        String mobilePhoneNumber,
        String rawPassword
) {
}
