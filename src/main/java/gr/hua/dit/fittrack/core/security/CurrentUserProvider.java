package gr.hua.dit.fittrack.core.security;

import gr.hua.dit.fittrack.core.model.PersonType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Provides access to the currently authenticated user.
 */
@Component
public final class CurrentUserProvider {

    public Optional<CurrentUser> getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            return Optional.empty();
        }

        if (authentication.getPrincipal() instanceof ApplicationUserDetails userDetails) {
            return Optional.of(
                    new CurrentUser(
                            userDetails.personId(),
                            userDetails.getUsername(),
                            userDetails.type()
                    )
            );
        }

        return Optional.empty();
    }

    public CurrentUser requireCurrentUser() {
        return getCurrentUser()
                .orElseThrow(() -> new SecurityException("User not authenticated"));
    }

    public long requireCustomerId() {
        CurrentUser user = requireCurrentUser();
        if (user.type() != PersonType.CUSTOMER) {
            throw new SecurityException("CUSTOMER role required");
        }
        return user.id();
    }

    public long requireTrainerId() {
        CurrentUser user = requireCurrentUser();
        if (user.type() != PersonType.TRAINER) {
            throw new SecurityException("TRAINER role required");
        }
        return user.id();
    }
}
