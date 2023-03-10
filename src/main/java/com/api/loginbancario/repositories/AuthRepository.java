package com.api.loginbancario.repositories;

import com.api.loginbancario.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface AuthRepository extends JpaRepository<AppUser, Integer> {
    Optional<AppUser> findByAccountNumber(String accountNumber);
}