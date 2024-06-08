package com.example.springbootdeveloper.repository;

import com.example.springbootdeveloper.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * RefreshToken 레포지토리
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    /**
     * 사용자 ID로 RefreshToken 조회
     *
     * @param userId 사용자 ID
     * @return RefreshToken 정보
     */
    Optional<RefreshToken> findByUserId(Long userId);

    /**
     * RefreshToken으로 RefreshToken 조회
     *
     * @param refreshToken RefreshToken
     * @return RefreshToken 정보
     */
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    /**
     * 사용자 ID로 RefreshToken 삭제
     *
     * @param userId 사용자 ID
     */
    void deleteByUserId(Long userId);

    /**
     * RefreshToken으로 RefreshToken 삭제
     *
     * @param refreshToken RefreshToken
     */
    void deleteByRefreshToken(String refreshToken);
}
