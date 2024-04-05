package com.gdsc.todo.global.config.security.jwt;

import com.gdsc.todo.global.config.security.jwt.dto.TokenResponse;
import com.gdsc.todo.global.config.security.jwt.service.TokenCacheService;
import com.gdsc.todo.global.details.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final TokenCacheService tokenCacheService;
    private final CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request, response); // "/login" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }
        String refreshToken = tokenService.extractRefreshToken(request)
                .filter(tokenService::validateToken)
                .orElse(null);


        if (refreshToken != null) {
            checkRefreshTokenAndReIssueAccessToken(refreshToken, response);
            return;
        }

        // access-token이 만료되지 않았을때 수행되는 로직
        checkAccessTokenAndAuthentication(request, response, filterChain);

    }

    /**
     * 액세스토큰 검증 및 시큐리티 컨텍스트에 유저 등록
     * RTR 방식으로 프론트에서 액세스토큰의 만료여부를 확인하고 헤더에 보내어지기 때문에, 여기서는 단순히 액세스토큰의 오류를 검증
     * <p>
     * 1. (검증 성공시) email로 부터 유저정보를 가져와 SecurityContext에 등록
     * 2. (검증 실패시) 에러 발생
     *
     * @param request
     */
    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.debug("Access 토큰 확인 및 검증");
        String accessToken = tokenService.extractAccessToken(request)
                .filter(tokenService::validateToken)
                .orElse(null);

        if (accessToken == null) {
            log.warn("Access 토큰이 없습니다.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access token is missing or invalid");
            return;
        }


        Authentication authentication = getAuthentication(accessToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }


    /**
     * 이 로직이 수행되는 순간 AccessToken은 만료된 상황
     * <p>
     * RefreshToken cache 저장 ->  (key,value) : ( 'email', 'refreshToken')
     *
     * @param refreshToken
     * @param response
     * @return 새로 발급한 AccessToken, RefreshToken을 response 헤더에 저장
     */
    private void checkRefreshTokenAndReIssueAccessToken(String refreshToken,
                                                        HttpServletResponse response) {
        log.info("Refresh 토큰 확인 및 검증");
        TokenResponse tokenResponse = tokenCacheService.reIssueToken(refreshToken);
        tokenService.sendAccessAndRefreshToken(response, tokenResponse.accessToken(), tokenResponse.refreshToken());
    }

    private Authentication getAuthentication(String token) {
        String email = tokenService.resolveSubject(token);

        if (email != null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);
            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String[] excludePath = {
                "/swagger-ui/**",
                "/v3/api-docs/**", "/swagger-ui/index.html",
                "/swagger-ui/swagger-ui-standalone-preset.js", "/swagger-ui/swagger-initializer.js",
                "/swagger-ui/swagger-ui-bundle.js", "/swagger-ui/swagger-ui.css",
                "/swagger-ui/index.css", "/swagger-ui/favicon-32x32.png",
                "/swagger-ui/favicon-16x16.png",
                "/api-docs/json/swagger-config", "/api-docs/json",
                "/v3/api-docs/swagger-config", "/v3/api-docs",
        };
        String path = request.getRequestURI();
        return Arrays.stream(excludePath).anyMatch(path::startsWith);
    }
}
