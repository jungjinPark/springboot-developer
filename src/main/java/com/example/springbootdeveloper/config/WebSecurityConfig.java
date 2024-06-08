package com.example.springbootdeveloper.config;

import com.example.springbootdeveloper.service.UserDetailService;
import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userDetailService;

    // 필터 적용 제외 설정
    @Bean
    public WebSecurityCustomizer webSecrityCustomizer() {
        return (web) -> {
            // 필터 적용 제외 설정
            WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer = web.ignoring()
                    .requestMatchers(
                            new AntPathRequestMatcher("/css/**"), // CSS
                            new AntPathRequestMatcher("/js/**"), // JS
                            new AntPathRequestMatcher("/images/**"), // 이미지
                            new AntPathRequestMatcher("/webjars/**") // 웹자원
                    );
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(auth -> auth // 요청 권한 설정
                        .requestMatchers(
                                new AntPathRequestMatcher("/user"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/logout")
                        ).permitAll() // 로그인, 회원가입, 로그아웃 페이지는 인증 없이 접근 가능
                        .anyRequest().authenticated()) // 모든 요청에 대해 인증 필요
                .formLogin(formLogin -> formLogin // 로그인 설정
                        .loginPage("/login") // 로그인 페이지 URL
                        .defaultSuccessUrl("/articles")) // 로그인 성공 URL
                .logout(logout -> logout // 로그아웃 설정
                        .logoutUrl("/logout") // 로그아웃 URL
                        .logoutSuccessUrl("/login") // 로그아웃 성공 URL
                        .invalidateHttpSession(true)) // HTTP 세션 무효화
                .csrf(AbstractHttpConfigurer::disable); // CSRF 비활성화

        return http.build();
    }

    // 인증 관리자 빈 등록
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder, UserDetailService userDetailService) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(daoAuthenticationProvider);
    }

    // 비밀번호 암호화 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
