package com.licode.prodigoerp.common.security;

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
}
