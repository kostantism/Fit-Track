package gr.hua.dit.fittrack.core.service.model;

import gr.hua.dit.fittrack.core.model.PersonType;
import jakarta.validation.constraints.*;

/**
 * DTO for requesting the creation (registration) of a Person.
 */
public record CreatePersonRequest(
        @NotNull PersonType type,
        @NotNull @NotBlank @Size(max = 100) String firstName,
        @NotNull @NotBlank @Size(max = 100) String lastName,
        @NotNull @NotBlank @Size(max = 100) @Email String emailAddress,
        @NotNull @NotBlank @Size(max = 18) @Pattern(regexp = "^\\+?[0-9]{12,15}$", message = "Invalid Phone Number Format") String mobilePhoneNumber,
        @NotNull @NotBlank @Size(min = 4, max = 24) String rawPassword
) {
}
