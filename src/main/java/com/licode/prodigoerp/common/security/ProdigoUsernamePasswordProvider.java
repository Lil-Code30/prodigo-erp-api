package com.licode.prodigoerp.common.security;

import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ProdigoUsernamePasswordProvider implements AuthenticationProvider {


    private UserRepository userRepository;

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username  = authentication.getName();
        String password = authentication.getCredentials().toString();

        User user = userRepository.findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User details not found for the user: " + username)
                );

        List<String> roles = userRepository.findActiveRoleNamesByUserId();

        List<SimpleGrantedAuthority> authorities = List.of(

        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
