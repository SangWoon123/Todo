package com.gdsc.todo.domain.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private Long id;
    private String loginId;
    private String email;
    private String password;
}
