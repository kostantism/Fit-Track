package gr.hua.dit.fittrack.core.security;

import gr.hua.dit.fittrack.core.model.User;
import gr.hua.dit.fittrack.core.repository.UserRepository;
import gr.hua.dit.fittrack.core.security.AuthService;
import gr.hua.dit.fittrack.core.security.AuthUser;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(
            final UserRepository userRepository,
            final PasswordEncoder passwordEncoder
    ) {
        if (userRepository == null) throw new NullPointerException();
        if (passwordEncoder == null) throw new NullPointerException();

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<AuthUser> authenticate(final String username, final String password) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException();
        if (password == null || password.isBlank()) throw new IllegalArgumentException();

        return userRepository.findByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(this::toAuthUser);
    }

    private AuthUser toAuthUser(final User user) {
        return new AuthUser(
                user.getId(),
                user.getUsername(),
                Set.of(user.getRole().name())
        );
    }
}
