package com.licode.prodigoerp.user.service;

import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserPrincipal;
import com.licode.prodigoerp.user.repository.UserRepository;
import com.licode.prodigoerp.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user = userRepository.findUserByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("User details not found for the user: " + username));

        List<String> roles = userRoleRepository.findActiveRoleNameByUserId(user.getId());
        List<String> permissions = userRoleRepository.findActivePermissionCodesByUserId(user.getId());

        Collection<GrantedAuthority> authorities = Stream.concat(
                roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_" + r)),
                permissions.stream().map(p -> new SimpleGrantedAuthority("PERM_" + p))
        ).collect(Collectors.toSet());

        return new UserPrincipal(user, authorities);
    }
}
