package com.licode.prodigoerp.auth.adapter.output.persistence.user;

import com.licode.prodigoerp.auth.adapter.input.rest.dto.RegisterRequestDto;
import com.licode.prodigoerp.auth.application.port.output.query.UserQueryRepositoryPort;
import com.licode.prodigoerp.auth.application.port.output.command.UserCommandRepositoryPort;
import com.licode.prodigoerp.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserCommandRepositoryPort, UserQueryRepositoryPort {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User registerUser(RegisterRequestDto registerRequestDto) { // TODO: find the datatype to be passe here
        // TODO: need to do all the steps for the registration

        return new User();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        return jpaUserRepository.findByEmail(email).map(UserJpaMapper::toDomainModel);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {

        return jpaUserRepository.findByUsername(username).map(UserJpaMapper::toDomainModel);
    }
}
