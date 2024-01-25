package com.gdsc.todo.global.oauth.handler;

import com.gdsc.todo.global.token.RefreshTokenService;
import com.gdsc.todo.global.token.TokenService;
import com.gdsc.todo.global.oauth.CustomOAuth2User;
import com.gdsc.todo.global.token.repository.RefreshTokenRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class Oauth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인성공");
        CustomOAuth2User oAuth2User= (CustomOAuth2User) authentication.getPrincipal();
        // accessToken
        String accessToken = tokenService.generateAccessToken(oAuth2User.getEmail());
        String refreshToken=tokenService.generateAccessToken(oAuth2User.getEmail());


        log.info("token생성 ={}",accessToken);
        log.info("refresh생성 ={}",refreshToken);

        tokenService.sendAccessAndRefreshToken(response,accessToken,refreshToken);
        refreshTokenService.updateRefreshToken(oAuth2User.getEmail(), refreshToken);
    }
}
