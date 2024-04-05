package com.gdsc.todo.global.config.security.jwt;

import com.gdsc.todo.global.error.ErrorCode;
import com.gdsc.todo.global.error.InvalidRequestException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class TokenService {

    private final String SECRET_KEY;
    private String accessHeader;
    private String refreshHeader;
    private long accessExpiration;
    private long refreshExpiration;
    private static final String BEARER = "Bearer ";

    public TokenService(@Value("${jwt.secret}") String SECRET_KEY,
                        @Value("${jwt.access-expiration}") long accessExpiration,
                        @Value("${jwt.refresh-expiration}") long refreshExpiration,
                        @Value("${jwt.access-header}") String accessHeader,
                        @Value("${jwt.refresh-header}") String refreshHeader) {
        this.SECRET_KEY = SECRET_KEY;
        this.accessExpiration = accessExpiration;
        this.refreshExpiration = refreshExpiration;
        this.accessHeader = accessHeader;
        this.refreshHeader = refreshHeader;
    }

    public String generateToken(long expiration, String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // HS512과 비교했을때 본 서비스는 암호화 난이도를 낮게 설정
                .compact();
    }

    // JWT 토큰 생성
    public String generateAccessToken(String email) {
        return generateToken(accessExpiration, email);
    }

    public String generateRefreshToken(String email) {
        return generateToken(refreshExpiration, email);
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException e) {
            throw new InvalidRequestException(ErrorCode.INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 액세스 토큰");
            throw new InvalidRequestException(ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new InvalidRequestException(ErrorCode.INVALID_TOKEN);
        } catch (MalformedJwtException e) {
            throw new InvalidRequestException(ErrorCode.INVALID_PERMISSION);
        } catch (IllegalArgumentException e) {
            log.warn("액세스 토큰이 존재하지 않는 요청.");
            throw new InvalidRequestException(ErrorCode.NOT_ACCESS_TOKEN);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }

    public String resolveSubject(String token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claim.getSubject();
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response,
                                          String accessToken,
                                          String refreshToken
    ) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        log.info("Access Token 헤더 설정");
        response.setHeader(accessHeader, BEARER + accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        log.info("Refresh Token 헤더 설정");
        response.setHeader(refreshHeader, BEARER + refreshToken);
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(accessToken -> accessToken.startsWith(BEARER))
                .map(accessToken -> accessToken.replace(BEARER, ""));
    }

}
