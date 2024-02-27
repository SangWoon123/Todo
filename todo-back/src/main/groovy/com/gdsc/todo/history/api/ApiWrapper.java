package com.gdsc.todo.history.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class ApiWrapper<T> {
    private T data;
    private HttpStatus httpStatus;

    public ApiWrapper(T data, HttpStatus httpStatus) {
        this.data = data;
        this.httpStatus = httpStatus;
    }
}
