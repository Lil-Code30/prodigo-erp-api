package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.User;

import java.util.Optional;

public interface UserQueryRepositoryPort {

    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);

}
