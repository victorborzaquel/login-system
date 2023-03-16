package com.vb.loginsystem.security.tokens.logtoken;

import com.vb.loginsystem.security.auth.Auth;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LogToken implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank
    private String token;
    @Enumerated(STRING)
    @Builder.Default
    private LogTokenType logTokenType = LogTokenType.BEARER;
    @Builder.Default
    private Boolean expired = false;
    @Builder.Default
    private Boolean revoked = false;
    @ManyToOne
    @NotNull
    private Auth auth;

    public Boolean isExpired() {
        return expired;
    }

    public Boolean isRevoked() {
        return revoked;
    }
}
