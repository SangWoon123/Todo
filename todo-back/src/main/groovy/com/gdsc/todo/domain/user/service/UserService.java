package com.gdsc.todo.domain.user.service;

import com.gdsc.todo.domain.user.repository.UserRepository;
import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public void updateAuthority(String email, Role authority) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("유저가 존재하지 않습니다."));
    }
}
