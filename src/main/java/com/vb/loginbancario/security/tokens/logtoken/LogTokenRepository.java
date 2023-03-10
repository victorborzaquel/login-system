package com.vb.loginbancario.security.tokens.logtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LogTokenRepository extends JpaRepository<LogToken, Long> {
    @Query("SELECT lt FROM LogToken lt INNER JOIN Auth a on lt.auth.id = a.id WHERE a.id = ?1 and (lt.expired = false or lt.revoked = false)")
    List<LogToken> findAllByValidTokensByUser(Long userId);
    Optional<LogToken> findByToken(String token);
}
