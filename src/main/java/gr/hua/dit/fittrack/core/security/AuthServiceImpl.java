package gr.hua.dit.fittrack.core.security;

import gr.hua.dit.fittrack.core.model.Person;
import gr.hua.dit.fittrack.core.repository.PersonRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            final PersonRepository personRepository,
            final PasswordEncoder passwordEncoder
    ) {
        if (personRepository == null) throw new NullPointerException();
        if (passwordEncoder == null) throw new NullPointerException();

        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AuthUser> authenticate(final String email, final String password) {
        if (email == null || email.isBlank()) throw new IllegalArgumentException();
        if (password == null || password.isBlank()) throw new IllegalArgumentException();

        return personRepository.findByEmailAddressIgnoreCase(email)
                .filter(person -> passwordEncoder.matches(password, person.getPasswordHash()))
                .map(this::toAuthUser);
    }

    private AuthUser toAuthUser(final Person person) {
        return new AuthUser(
                person.getId(),
                person.getEmailAddress(),
                Set.of(person.getType().name())
        );
    }
}
