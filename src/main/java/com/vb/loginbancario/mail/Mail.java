package com.vb.loginbancario.mail;

import com.vb.loginbancario.data.enums.EmailStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Mail {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long ownerRef;
    @NotBlank
    @Email
    private String emailFrom;
    @NotBlank
    @Email
    private String emailTo;
    @NotBlank
    private String subject;
    @NotBlank
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime sendAt;
    @Enumerated(STRING)
    @Builder.Default
    private EmailStatus emailStatus = EmailStatus.PENDING;
}
