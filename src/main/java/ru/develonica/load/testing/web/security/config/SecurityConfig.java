package ru.develonica.load.testing.web.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import ru.develonica.load.testing.web.security.handler.OpenIdLogoutHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final OpenIdLogoutHandler keycloakLogoutHandler;

    public SecurityConfig(OpenIdLogoutHandler keycloakLogoutHandler) {
        this.keycloakLogoutHandler = keycloakLogoutHandler;
    }

    @Bean
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
    }

    @Order(1)
    @Bean
    public SecurityFilterChain clientFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        requests -> requests.requestMatchers("/")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .logout(logoutConfigurer -> logoutConfigurer.addLogoutHandler(keycloakLogoutHandler).logoutSuccessUrl("/"))
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }
}
