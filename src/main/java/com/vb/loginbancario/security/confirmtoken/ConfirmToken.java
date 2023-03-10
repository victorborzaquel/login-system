package com.vb.loginbancario.security.confirmtoken;

import com.vb.loginbancario.security.auth.Auth;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfirmToken {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String token;
    @NotNull
    private LocalDateTime createdAt;
    @NotNull
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    @NotNull
    @ManyToOne
    private Auth auth;
}
