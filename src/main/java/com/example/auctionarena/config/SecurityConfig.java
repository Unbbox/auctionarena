package com.example.auctionarena.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.auctionarena.handler.CustomAccessDeniedHandler;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http.authorizeHttpRequests(authorize -> authorize
                                // 전체 접근
                                // .anyRequest()
                                // .permitAll()

                                // 접근 제한
                                .requestMatchers("/", "/css/**", "/fonts/**", "/img/**", "/js/**",
                                                "/noticejs/**", "/memberjs/**", "/saas/**",
                                                "/videos/**", "/auth")
                                .permitAll()
                                .requestMatchers("/auctionArena/product_details",
                                                "/auctionArena/categories")
                                .permitAll()
                                .requestMatchers("/notice/notice", "/notice/notice-details").permitAll()
                                .requestMatchers("/upload/display").permitAll()
                                .requestMatchers("/member/signup", "/member/find-password",
                                                "/member/edit-password")
                                .permitAll()
                                .requestMatchers("/access-denied").permitAll()
                                .anyRequest().authenticated());

                // 로그인
                http.formLogin(login -> login
                                .loginPage("/member/login")
                                .permitAll()
                                .defaultSuccessUrl("/", true));
                // .rememberMe(remember -> remember.rememberMeServices(remembermMeServices))

                // 소셜로그인
                http.oauth2Login(oauth2Login -> oauth2Login
                                .loginPage("/member/login")
                                .permitAll()
                                .defaultSuccessUrl("/", true));

                // 로그아웃
                http.logout(logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                .logoutSuccessUrl("/"));

                // 토큰 비활성화
                // http.csrf(csrf -> csrf.disable());

                http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS));

                // 403 접근제한
                http.exceptionHandling(exception -> exception.accessDeniedHandler(customAccessDeniedHandler()));

                return http.build();
        }

        // 자동 로그인 처리 - 쿠키 이용
        // securityFilterChain 매개변수 RememberMeServices remembermMeServices 넣기
        // @Bean
        // RememberMeServices rememberMeServices(UserDetailsService userDetailsService)
        // {
        // // 비밀번호 알고리즘 사용 - 암호화 : RememberMeTokenAlgorithm.SHA256;
        // RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
        // // myKey 라는 이름으로 암호화한 비밀번호 저장
        // TokenBasedRememberMeServices rememberMeServices = new
        // TokenBasedRememberMeServices("myKey", userDetailsService,
        // encodingAlgorithm);
        // rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 7일간 : 쿠키 만료
        // 시간 설정 (무한정 x)
        // return rememberMeServices;
        // }

        @Bean
        PasswordEncoder passwordEncoder() {
                return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }

        CustomAccessDeniedHandler customAccessDeniedHandler() {
                return new CustomAccessDeniedHandler();
        }
}
