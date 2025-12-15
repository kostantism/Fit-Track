package gr.hua.dit.fittrack.core.security;

import java.util.Set;

public record AuthUser(
        Long  id,
        String password,
        Set<String> roles
) {}
