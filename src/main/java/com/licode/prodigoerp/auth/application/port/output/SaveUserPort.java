package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.User;

public interface SaveUserPort {

    User save(User user);

}
