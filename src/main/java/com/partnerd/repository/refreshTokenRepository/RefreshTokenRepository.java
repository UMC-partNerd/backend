package com.partnerd.repository.refreshTokenRepository;

import com.partnerd.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
