package com.gdsc.todo.global.oauth;

import com.gdsc.todo.global.TokenService;
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

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("로그인성공");
        CustomOAuth2User oAuth2User= (CustomOAuth2User) authentication.getPrincipal();
        // accessToken
        String accessToken = tokenService.generateAccessToken(oAuth2User.getEmail());
        tokenService.sendAccessToken(response,accessToken);
    }
}
