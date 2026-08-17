package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {

    User registerUser(User user);

}
