package com.project.rbac.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        //비밀번호 생성시 앞쪽에 암호화형식을 집어넣어 사용자 인증시 security가 예측가능하게 해줌
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // ✅ 최신 방식: Lambda DSL 사용
                .csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화
                .headers(headers -> headers.frameOptions().disable()) // H2 콘솔을 위한 frame 옵션 비활성화
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED).maximumSessions(1)) 
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/**").hasRole("A")
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/auth/login").permitAll() // 로그인 엔드포인트는 인증 없이 접근 가능
                        .requestMatchers("/swagger-ui/**","/h2-console/**").permitAll()
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                ).formLogin(AbstractHttpConfigurer::disable)
                .logout(logout -> logout
                        .logoutUrl("/auth/logout")
                        .logoutSuccessUrl("/auth/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }
}