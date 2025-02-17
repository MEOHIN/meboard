package com.meohin.meboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeHttpRequests(
                (authorizeHttpRequests) -> 
                    authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**"))
                        .permitAll()
            );

        httpSecurity.formLogin((formLogin) -> formLogin.loginPage("/user/login").defaultSuccessUrl("/"));
        httpSecurity.logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout")).logoutSuccessUrl("/").invalidateHttpSession(true));
        
        return httpSecurity.build();
    }

    // PasswordEncoder를 빈(bean)으로 등록
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager 빈을 으로 등록; AuthenticationManager 는 스프링 시큐리티의 인증을 담당한다.
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}