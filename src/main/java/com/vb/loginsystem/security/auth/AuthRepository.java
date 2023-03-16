package com.vb.loginsystem.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AuthRepository extends JpaRepository<Auth, Integer> {
    Optional<Auth> findByEmail(String username);
}