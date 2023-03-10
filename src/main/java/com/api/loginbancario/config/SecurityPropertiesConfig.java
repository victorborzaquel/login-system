package com.api.loginbancario.config;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "security.config")
public class SecurityPropertiesConfig {
    @NotBlank
    private static String key;
    private static Long expiration;
}
