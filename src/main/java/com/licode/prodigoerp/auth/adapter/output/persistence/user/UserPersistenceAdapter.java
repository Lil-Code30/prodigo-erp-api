package com.licode.prodigoerp.auth.adapter.output.persistence.user;

import com.licode.prodigoerp.auth.application.port.output.LoadUserPort;
import com.licode.prodigoerp.auth.application.port.output.SaveUserPort;
import com.licode.prodigoerp.auth.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserPersistenceAdapter implements SaveUserPort, LoadUserPort {

    private final JpaUserRepository jpaUserRepository;

    @Override
    public User save(User user) { // TODO: find the datatype to be passe here
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
