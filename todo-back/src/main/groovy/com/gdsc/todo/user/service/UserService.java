package com.gdsc.todo.user.service;

import com.gdsc.todo.global.token.TokenService;
import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.user.dao.User;
import com.gdsc.todo.user.dto.UserDto;
import com.gdsc.todo.user.dto.UserResponse;
import com.gdsc.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    //회원가입
    public User signup(UserDto userDto){

        User user=User.builder()
                .username(userDto.getId())
                .password(userDto.getPassword())
                .email(userDto.getEmail())
                .role(Role.USER)
                .build();
        User save = userRepository.save(user);

        return save;
    }

    public UserResponse signIn(UserDto userDto){
        User user=userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(()->new IllegalArgumentException("유저가 존재하지않습니ㅏㄷ."));

        String token = tokenService.generateAccessToken(user.getEmail());
        return UserResponse.builder()
                .accessToken(token)
                .build();
    }

}
