package com.gdsc.todo.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰입니다."),
    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자가 권한이 없습니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "시그니처가 일치하지 않습니다."),
    NOT_ACCESS_TOKEN(HttpStatus.NOT_FOUND, "토큰이 존재하지 않습니다."),
    FORBIDDEN_REQUEST(HttpStatus.FORBIDDEN, "ADMIN 회원만 접근할 수 있습니다.");

    private final HttpStatus status;
    private final String message;
}
