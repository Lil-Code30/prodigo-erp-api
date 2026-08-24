package com.licode.prodigoerp.auth.application.port.input;

import com.licode.prodigoerp.auth.application.port.input.command.RefreshResponseCommand;

public interface RefreshTokenUseCase {
    RefreshResponseCommand refreshToken(String refreshToken);
}
