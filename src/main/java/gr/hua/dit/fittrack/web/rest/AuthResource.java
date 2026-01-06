package gr.hua.dit.fittrack.web.rest;

import gr.hua.dit.fittrack.core.security.AuthService;
import gr.hua.dit.fittrack.core.security.AuthUser;
import gr.hua.dit.fittrack.core.security.JwtService;
import gr.hua.dit.fittrack.core.service.PersonBusinessLogicService;
import gr.hua.dit.fittrack.core.service.model.CreatePersonRequest;
import gr.hua.dit.fittrack.core.service.model.CreatePersonResult;
import gr.hua.dit.fittrack.web.rest.model.LoginRequest;
import gr.hua.dit.fittrack.web.rest.model.LoginResponse;
import gr.hua.dit.fittrack.web.rest.model.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * REST controller for authentication (JWT).
 */
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthResource {

    private final AuthService authService;
    private final JwtService jwtService;
    private final PersonBusinessLogicService personBusinessLogicService;

    public AuthResource(final AuthService authService,
                        final JwtService jwtService,
                        PersonBusinessLogicService personBusinessLogicService) {
        if (authService == null) throw new NullPointerException();
        if (jwtService == null) throw new NullPointerException();
        if (personBusinessLogicService == null) throw new NullPointerException();

        this.authService = authService;
        this.jwtService = jwtService;
        this.personBusinessLogicService = personBusinessLogicService;
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

    @PostMapping("/register")
    public CreatePersonResult register(
            @RequestBody @Valid RegisterRequest request
    ) {
        return personBusinessLogicService.createPerson(
                new CreatePersonRequest(
                        request.type(),
                        request.firstName(),
                        request.lastName(),
                        request.emailAddress(),
                        request.mobilePhoneNumber(),
                        request.password()
                )
        );
    }

}
