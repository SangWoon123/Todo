package com.gdsc.todo.global.token;

import com.gdsc.todo.global.error.ErrorCode;
import com.sun.jdi.request.InvalidRequestStateException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

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

    private String generateToken(long expiration, String email) {
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

    // JWT 토큰에서 인증 정보 조회

    // 토큰에서 회원 정보 추출

    // Request의 Header에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && StringUtils.startsWithIgnoreCase(bearerToken,
                "Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getBody();
            return true;
        } catch (SignatureException e) {
            throw new InvalidRequestStateException(ErrorCode.INVALID_JWT_SIGNATURE.getMessage());
        } catch (ExpiredJwtException e) {
            throw new InvalidRequestStateException(ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new InvalidRequestStateException(ErrorCode.INVALID_TOKEN.getMessage());
        } catch (MalformedJwtException e) {
            throw new InvalidRequestStateException(ErrorCode.INVALID_PERMISSION.getMessage());
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
}
