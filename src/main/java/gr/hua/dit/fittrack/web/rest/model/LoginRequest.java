package gr.hua.dit.fittrack.web.rest.model;

import gr.hua.dit.fittrack.web.rest.AuthResource;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * @see AuthResource
 */
public record LoginRequest(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password
) {}
