package gr.hua.dit.fittrack.core.security;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.repository.PersonRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of Spring's {@code UserDetailsService} for providing application users.
 */
@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;

    public ApplicationUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return personRepository
                .findByEmailAddressIgnoreCase(username)
                .map(person -> new ApplicationUserDetails(
                        person.getId(),
                        person.getEmailAddress(),
                        person.getPasswordHash(),
                        person.getType()
                ))
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username)
                );
    }

        // One-line alternative:
        /*
        return this.personRepository.findByEmailAddressIgnoreCase(username.strip())
            .map(person -> new ApplicationUserDetails(
                    person.getId(),
                    person.getEmailAddress(),
                    person.getPasswordHash(),
                    person.getType())
            )
            .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
        */
}

