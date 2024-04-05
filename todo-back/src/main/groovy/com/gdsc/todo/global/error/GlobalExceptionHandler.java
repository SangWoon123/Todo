package com.gdsc.todo.global.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<ErrorResponse> businessHandler(BusinessException e){
        log.warn("handleBusinessException",e);
        final ErrorCode errorCode=e.getErrorCode();
        final ErrorResponse errorResponse=ErrorResponse.builder()
                .message(errorCode.getMessage())
                .status(errorCode.getStatus())
                .build();
        return new ResponseEntity<>(errorResponse, errorCode.getStatus());
    }
}
