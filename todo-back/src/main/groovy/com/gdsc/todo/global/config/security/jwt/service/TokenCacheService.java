package com.gdsc.todo.global.config.security.jwt.service;


import com.gdsc.todo.global.config.security.jwt.TokenService;
import com.gdsc.todo.global.config.security.jwt.dto.TokenResponse;
import com.gdsc.todo.global.config.security.jwt.repository.TokenInfoCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenCacheService {


    private final TokenService tokenService;
    private final TokenInfoCacheRepository tokenInfoCacheRepository;


    public TokenResponse createNewToken(final String email) {
        TokenResponse tokenResponse = createTokenResponse(email);
        tokenInfoCacheRepository.save(tokenResponse.refreshToken(), email);
        return tokenResponse;
    }

    public TokenResponse reIssueToken(final String refreshToken) {
        String email = tokenService.resolveSubject(refreshToken);
        String userEmail = tokenInfoCacheRepository.getUserInfo(refreshToken)
                .orElseThrow(IllegalAccessError::new);

        String newRefreshToken = tokenService.generateRefreshToken(email);
        tokenInfoCacheRepository.rename(refreshToken, newRefreshToken);

        return createTokenResponse(userEmail);
    }

    private TokenResponse createTokenResponse(final String email) {
        return new TokenResponse(
                tokenService.generateAccessToken(email),
                tokenService.generateRefreshToken(email)
        );
    }
}
