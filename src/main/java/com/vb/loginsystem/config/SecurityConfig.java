package com.vb.loginsystem.config;

import com.vb.loginsystem.security.handler.CustomAccessDeniedHandler;
import com.vb.loginsystem.security.handler.CustomAuthenticationEntryPoint;
import com.vb.loginsystem.security.jwt.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors(withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/v1/auth/**")
                            .permitAll()
                        .requestMatchers("/api-docs/**", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html")
                            .permitAll()
                        .requestMatchers("/api/v1/admin/**")
                            .hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/api/v1/oauth")
                        .authorizationEndpoint(authorization -> authorization
                                .baseUri("/api/v1/oauth/authorize")
                        )
                        .defaultSuccessUrl("/api/v1/oauth/success")
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http.build();
    }
}
