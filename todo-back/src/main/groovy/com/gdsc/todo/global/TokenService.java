package com.gdsc.todo.global;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Slf4j
public class TokenService {

    private final String SECRET_KEY;
    private String accessHeader;
    private long accessExpiration;
    private static final String BEARER = "Bearer ";

    public TokenService(@Value("${jwt.secret}") String SECRET_KEY,
                        @Value("${jwt.expiration}") long accessExpiration,
                        @Value("${jwt.header}") String accessHeader) {
        this.SECRET_KEY = SECRET_KEY;
        this.accessExpiration = accessExpiration;
        this.accessHeader = accessHeader;
    }

    // JWT 토큰 생성
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // HS512과 비교했을때 본 서비스는 암호화 난이도를 낮게 설정
                .compact();
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
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }


    public String resolveSubject(String token) {
        Claims claim = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claim.getSubject();
    }

    public void sendAccessToken(HttpServletResponse response,
                                String accessToken
    ) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        log.info("Access Token 헤더 설정");
        response.setHeader(accessHeader, BEARER + accessToken);
    }
}
