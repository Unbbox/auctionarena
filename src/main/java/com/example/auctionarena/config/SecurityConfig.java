package com.example.auctionarena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                // 전체 접근
                                .anyRequest().permitAll()

                // 접근 제한
                // .requestMatchers("/", "/css/**", "/fonts/**", "/img/**", "/js/**",
                // "/noticejs/**", "/saas/**",
                // "/videos/**", "/auth")
                // .permitAll()
                // .requestMatchers("/auctionArena/product_details",
                // "/auctionArena/categories").permitAll()
                // .requestMatchers("/notice/notice", "/notice/notice-details").permitAll()
                // .requestMatchers("/member/signup").permitAll()
                // .anyRequest().authenticated()
                );

                http.formLogin(login -> login.loginPage("/member/login").permitAll()
                                .defaultSuccessUrl("/", true));

                http.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                .logoutSuccessUrl("/"));

                // 토큰 비활성화
                // http.csrf(csrf -> csrf.disable());

                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

                return http.build();
        }

        @Bean
        PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
}
