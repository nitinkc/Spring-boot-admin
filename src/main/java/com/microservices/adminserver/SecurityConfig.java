package com.microservices.adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Created by nichaurasia on Friday, January/24/2020 at 4:38 PM
 */

@Configuration
@EnableAutoConfiguration
@EnableAdminServer
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ignore1", "/ignore2");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((login) -> login
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers("/no-csrf"))
                .httpBasic(withDefaults());

        return http.build();
    }
}