package com.vb.loginsystem;

import com.vb.loginsystem.security.jwt.SecurityJwtPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityJwtPropertiesConfig.class)
public class LoginSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginSystemApplication.class, args);
    }

}
