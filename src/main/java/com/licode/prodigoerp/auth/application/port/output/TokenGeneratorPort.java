package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.User;

public interface TokenGeneratorPort {

    String generateAccessToken(User user);
}
