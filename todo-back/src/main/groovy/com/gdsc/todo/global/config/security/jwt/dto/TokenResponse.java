package com.gdsc.todo.global.config.security.jwt.dto;

public record TokenResponse(
        String accessToken,
        String refreshToken) {

}
