package com.vb.loginbancario;

import com.vb.loginbancario.security.jwt.SecurityJwtPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityJwtPropertiesConfig.class)
public class LoginBancarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginBancarioApplication.class, args);
    }

}
