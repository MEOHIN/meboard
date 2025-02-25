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
                .defaultSuccessUrl("/post/list", true));    //true 파라미터는 사용자가 특정 페이지를 요청한 후 로그인했을 때도 항상 지정된 URL로 리다이렉트

        httpSecurity.logout((logout) -> 
            logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true));

        return httpSecurity.build();
    }
}