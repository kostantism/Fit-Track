package gr.hua.dit.fittrack.core.security;

import gr.hua.dit.fittrack.core.model.PersonType;

/**
 * @see CurrentUserProvider
 */
public record CurrentUser(long id, String emailAddress, PersonType type) {}
