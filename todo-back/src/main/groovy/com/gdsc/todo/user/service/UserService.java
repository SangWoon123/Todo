package com.gdsc.todo.user.service;

import com.gdsc.todo.global.token.TokenService;
import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.user.dao.User;
import com.gdsc.todo.user.dto.UserResponse;
import com.gdsc.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
}
