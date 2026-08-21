package com.licode.prodigoerp.common_old.security;

import com.licode.prodigoerp.common_old.SystemConstants;
import com.licode.prodigoerp.auth.domain.exception.JwtValidationException;
import com.licode.prodigoerp.common_old.security.dto.JwtPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    // Never throws — returns empty if unauthenticated, anonymous, or on a public endpoint
    public static Optional<JwtPrincipal> getCurrentUserOptional() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof JwtPrincipal jwtPrincipal) {
            return Optional.of(jwtPrincipal);
        }

        // covers "anonymousUser" String principal from AnonymousAuthenticationToken
        return Optional.empty();
    }

    // Use on protected endpoints where a user is guaranteed — fails loudly if missing
    public static JwtPrincipal getCurrentUser() {
        return getCurrentUserOptional()
                .orElseThrow(() -> new JwtValidationException("Invalid token"));
    }

    // Use for audit fields (createdBy/updatedBy) — safe on public endpoints too
    public static String getCurrentUsernameOrElseSysName() {
        return getCurrentUserOptional()
                .map(JwtPrincipal::username)
                .orElse(SystemConstants.SYSTEM_NAME);
    }
}