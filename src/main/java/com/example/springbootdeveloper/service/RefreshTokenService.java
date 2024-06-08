package com.example.springbootdeveloper.service;

import com.example.springbootdeveloper.domain.RefreshToken;
import com.example.springbootdeveloper.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * RefreshToken 서비스
 */
@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * RefreshToken 저장
     *
     * @param refreshToken RefreshToken 정보
     */
    public void save(RefreshToken refreshToken) {
        refreshTokenRepository.save(refreshToken);
    }

    /**
     * 사용자 ID로 RefreshToken 조회
     *
     * @param userId 사용자 ID
     * @return RefreshToken 정보
     */
    public Optional<RefreshToken> findByUserId(Long userId) {
        return refreshTokenRepository.findByUserId(userId);
    }

    /**
     * RefreshToken으로 RefreshToken 조회
     *
     * @param refreshToken RefreshToken
     * @return RefreshToken 정보
     */
    public Optional<RefreshToken> findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken);
    }

    /**
     * RefreshToken 삭제
     *
     * @param refreshToken RefreshToken 정보
     */
    public void delete(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    /**
     * 사용자 ID로 RefreshToken 삭제
     *
     * @param userId 사용자 ID
     */
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    /**
     * RefreshToken으로 RefreshToken 삭제
     *
     * @param refreshToken RefreshToken
     */
    public void deleteByRefreshToken(String refreshToken) {
        refreshTokenRepository.deleteByRefreshToken(refreshToken);
    }

    /**
     * 모든 RefreshToken 삭제
     */
    public void deleteAll() {
        refreshTokenRepository.deleteAll();
    }
}
