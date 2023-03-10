package com.api.loginbancario;

import com.api.loginbancario.config.SecurityPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityPropertiesConfig.class)
public class LoginBancarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoginBancarioApplication.class, args);
    }

}
