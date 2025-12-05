package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.PersonType;

/**
 * PersonView (DTO) that includes only information to be exposed.
 */
public record PersonView(
        long id,
        String firstName,
        String lastName,
        String mobilePhoneNumber,
        String emailAddress,
        PersonType type
) {}
