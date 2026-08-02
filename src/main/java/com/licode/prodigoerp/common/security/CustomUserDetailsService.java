package com.licode.prodigoerp.common.security;

import com.licode.prodigoerp.common.exception.NotFoundException;
import com.licode.prodigoerp.user.entity.User;
import com.licode.prodigoerp.user.entity.UserPrincipal;
import com.licode.prodigoerp.user.repository.UserRepository;
import com.licode.prodigoerp.user.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public UserDetails loadUserByUsername(String username){

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));

        return buildPrincipal(user);
    }

    public UserPrincipal buildPrincipal(User user){

        List<String> roles = userRoleRepository.findActiveRoleNamesByUserId(user.getId());
        List<String> permissions = userRoleRepository.findActivePermissionCodesByUserId(user.getId());


        // concatenating the roles and permissions in one collection to be used as granted authority
        Collection<GrantedAuthority> authorities = Stream.concat(
                roles.stream().map(r -> "ROLE_" + r),
                permissions.stream().map(p -> "PERM_" + p)
        ).map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

        return new UserPrincipal(user, authorities);
    }


}
