package com.vb.loginsystem.security.tokens.confirmtoken;

import com.vb.loginsystem.security.auth.Auth;
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

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ConfirmToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
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
