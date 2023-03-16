package com.vb.loginsystem.security.tokens.confirmtoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfirmaTokenRepository extends JpaRepository<ConfirmToken, Long> {
    Optional<ConfirmToken> findByToken(String token);
}
