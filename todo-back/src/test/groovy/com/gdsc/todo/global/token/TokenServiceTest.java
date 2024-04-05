package com.gdsc.todo.global.token;

import com.gdsc.todo.global.config.security.jwt.TokenService;
import com.sun.jdi.request.InvalidRequestStateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private RefreshTokenService refreshTokenService;


    private static final String EMAIL="test@test.com";


    @Test
    @DisplayName("토큰 생성")
    public void create_Token() {
        //given
        String token = tokenService.generateAccessToken(EMAIL);
        //when
        String subject = tokenService.resolveSubject(token);
        //then
        Assertions.assertEquals(subject,EMAIL);
        assertTrue(tokenService.validateToken(token));
    }

    @Test
    @DisplayName("리프레시토큰 생성")
    public void create_refreshToken() {
        //given
        String refreshToken = tokenService.generateRefreshToken(EMAIL);
        //when
        String subject = tokenService.resolveSubject(refreshToken);
        //then
        Assertions.assertEquals(subject,EMAIL);
        assertTrue(tokenService.validateToken(refreshToken));
    }

    @Test
    @DisplayName("토큰 만료")
    public void expiration_token() {
        long expiration=0;
        String token = tokenService.generateToken(expiration,EMAIL);

        assertThrows(InvalidRequestStateException.class,()->tokenService.validateToken(token));
    }

    @Test
    @DisplayName("잘못된 토큰 양식")
    public void invalid_token() {
        String token = tokenService.generateAccessToken(EMAIL).substring(1);

        assertThrows(InvalidRequestStateException.class,()->tokenService.validateToken(token));
    }

    @Test
    @DisplayName("토큰 여부 체크")
    public void not_access_token() {
        String token = tokenService.generateAccessToken(EMAIL);

        // 토큰이 존재하므로 토큰 검증에 성공
        assertTrue(tokenService.validateToken(token));

        // 토큰이 없으므로 토큰 검증에 실패
        String finalToken = null;
        assertThrows(InvalidRequestStateException.class, () -> tokenService.validateToken(finalToken));
    }

    @Test
    @DisplayName("액세스토큰 만료시 리프레시로 갱신체크")
    public void new_accessToken(){
        //given
        String accessToken=tokenService.generateToken(0,EMAIL);
        String refreshToken=tokenService.generateRefreshToken(EMAIL);
        //when
        assertThrows(InvalidRequestStateException.class, () -> tokenService.validateToken(accessToken));
        //then
    }


}