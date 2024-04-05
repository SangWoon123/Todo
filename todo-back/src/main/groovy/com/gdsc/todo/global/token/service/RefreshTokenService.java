package com.gdsc.todo.global.token.service;

import com.gdsc.todo.global.token.dao.RefreshToken;
import com.gdsc.todo.global.token.repository.RefreshTokenRepository;
import com.gdsc.todo.user.domain.User;
import com.gdsc.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void updateRefreshToken(String email, String refreshToken) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(NoSuchElementException::new);

        RefreshToken findRefreshToken = refreshTokenRepository.findByUser(user)
                .orElse(null);

        if (findRefreshToken == null) {
            RefreshToken updateRefreshToken = RefreshToken.builder()
                    .refreshToken(refreshToken)
                    .user(user)
                    .build();
            refreshTokenRepository.save(updateRefreshToken);
            return;
        }

        findRefreshToken.update(refreshToken);
        log.info("User: {}의 리프레시 토큰 업데이트 완료", user);
    }

}
