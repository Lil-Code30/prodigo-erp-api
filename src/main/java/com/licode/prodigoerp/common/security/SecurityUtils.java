package com.licode.prodigoerp.common.security;

import com.licode.prodigoerp.common.SystemConstants;
import com.licode.prodigoerp.common.exception.JwtValidationException;
import com.licode.prodigoerp.common.security.dto.JwtPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class SecurityUtils {

    public static JwtPrincipal getCurrentUser(){

        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        if(principal instanceof JwtPrincipal){
            return (JwtPrincipal) principal;
        }

        throw new JwtValidationException("Invalid token");
    }

    // return the current user connected of else return the system name
    // this is useful to set the audit value (who did what in the system)
    public static String getCurrentUsernameOrElseSysName(){

        JwtPrincipal principal = getCurrentUser();

        return principal.userId() == null ? SystemConstants.SYSTEM_NAME : principal.username();
    }
}
