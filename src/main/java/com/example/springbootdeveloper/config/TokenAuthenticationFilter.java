package com.example.springbootdeveloper.config;

import com.example.springbootdeveloper.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Token 인증 필터
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    private final static String HEADER_AUTHORIATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    /**
     * 요청 헤더에서 토큰을 추출하여 유효한 토큰인지 검증하고, 유효하다면 SecurityContext에 인증정보를 저장
     *
     * @param request     요청
     * @param response    응답
     * @param filterChain 필터체인
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader(HEADER_AUTHORIATION);
        String token = getAccessToken(authorizationHeader);

        if (token != null && tokenProvider.validToken(token)) {
            // 토큰이 유효하면 SecurityContext에 인증정보(Authentication 객체)를 저장
            SecurityContextHolder.getContext().setAuthentication(tokenProvider.getAuthentication(token));
        }
    }

    /**
     * Authorization 헤더에서 토큰을 추출
     *
     * @param authorizationHeader Authorization 헤더
     * @return 토큰
     */
    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }

        return null;
    }
}
