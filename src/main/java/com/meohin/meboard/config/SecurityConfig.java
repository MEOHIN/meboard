package com.meohin.meboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((authorizeHttpRequests) -> {
            authorizeHttpRequests.requestMatchers("/login").permitAll();
            authorizeHttpRequests.requestMatchers("/register").permitAll();
            authorizeHttpRequests.requestMatchers("/style.css").permitAll();
            authorizeHttpRequests.anyRequest().authenticated();
        });

        httpSecurity.formLogin((formLogin) -> 
            formLogin
                .loginPage("/login")
                .defaultSuccessUrl("/"));

        httpSecurity.logout((logout) -> 
            logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true));

        return httpSecurity.build();
    }
}