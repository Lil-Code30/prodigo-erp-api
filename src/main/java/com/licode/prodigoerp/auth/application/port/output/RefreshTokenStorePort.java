package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenStorePort {
   Optional<RefreshToken> findRefreshTokenByTokenString(String tokenString);
   Optional<RefreshToken> findRefreshTokenByUser_Id(Long userId);
   RefreshToken createRefreshToken ();

}
