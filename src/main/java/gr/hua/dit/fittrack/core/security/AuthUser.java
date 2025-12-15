package gr.hua.dit.fittrack.core.security;

import java.util.Set;

public record AuthUser(
        String  id,
        String password,
        Set<String> roles
) {}
