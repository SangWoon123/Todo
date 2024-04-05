package com.gdsc.todo.global.config.security.jwt.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class TokenInfoCacheRepository {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final int MAXIMUM_REFRESH_TOKEN_EXPIRES_IN_DAY = 30;

    public void save(final String key, final String email) {
        redisTemplate.opsForValue().set(
                key,
                email,
                MAXIMUM_REFRESH_TOKEN_EXPIRES_IN_DAY,
                TimeUnit.DAYS
        );

    }

    public void rename(
            final String oldKey,
            final String newKey
    ) {
        redisTemplate.rename(oldKey, newKey);
    }

    public Optional<String> getUserInfo(final String infoKey) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(infoKey).toString());
    }
}
