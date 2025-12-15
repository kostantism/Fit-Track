package gr.hua.dit.fittrack.core.security;

import java.util.Optional;

/**
 * Service for authenticating application users.
 */
public interface AuthService {

    Optional<AuthUser> authenticate(String id, String password);
}
