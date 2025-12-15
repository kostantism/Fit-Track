package gr.hua.dit.fittrack.web.rest.model;

import gr.hua.dit.fittrack.web.rest.AuthResource;

/**
 * @see AuthResource
 */
public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {}
