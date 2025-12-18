package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.security.AuthService;
import gr.hua.dit.fittrack.core.security.AuthUser;
import gr.hua.dit.fittrack.core.security.JwtService;
import gr.hua.dit.fittrack.web.rest.model.LoginRequest;
import gr.hua.dit.fittrack.web.rest.model.LoginResponse;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for authentication (JWT).
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthResource(final AuthService authService,
                        final JwtService jwtService) {
        if (authService == null) throw new NullPointerException();
        if (jwtService == null) throw new NullPointerException();
        this.authService = authService;
        this.jwtService = jwtService;
    }

    /**
     * Login endpoint – issues JWT token.
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Valid LoginRequest request) {

        // Step 1: authenticate user
        final AuthUser authUser = authService
                .authenticate(request.email(), request.password())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.UNAUTHORIZED,
                                "Invalid email or password"
                        )
                );

        // Step 2: issue JWT
        final String token = jwtService.issue(authUser.id(), authUser.roles());
        return new LoginResponse(token, "Bearer", 60 * 60 // seconds (1h) – keep in sync with jwt ttl
        );
    }

}
