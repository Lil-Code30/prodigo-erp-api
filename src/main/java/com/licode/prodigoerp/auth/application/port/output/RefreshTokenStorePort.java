package com.licode.prodigoerp.auth.application.port.output;

import com.licode.prodigoerp.auth.domain.model.RefreshToken;
import com.licode.prodigoerp.auth.domain.model.User;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenStorePort {
   Optional<RefreshToken> findRefreshTokenByTokenString(String tokenString);
   Optional<RefreshToken> findRefreshTokenByUser_Id(UUID userId);
   RefreshToken createRefreshToken (User user);
   void revokeRefreshTokenByTokenString(String tokenString);

}
