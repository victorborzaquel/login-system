package com.vb.loginsystem.security.jwt;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.jwt")
public class SecurityJwtPropertiesConfig {
    @NotBlank
    private static String key;
    private static Long expiration;
}
