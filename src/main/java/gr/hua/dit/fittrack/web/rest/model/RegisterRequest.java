package gr.hua.dit.fittrack.web.rest.model;

import gr.hua.dit.fittrack.core.model.PersonType;
import jakarta.validation.constraints.*;

public record RegisterRequest(
        @NotNull PersonType type,
        @NotBlank String firstName,
        @NotBlank String lastName,
        @Email @NotBlank String emailAddress,
        @NotBlank String mobilePhoneNumber,
        @NotBlank String password
) {}