package com.gdsc.todo.user.api;

import com.gdsc.todo.user.dao.User;
import com.gdsc.todo.user.dto.UserDto;
import com.gdsc.todo.user.dto.UserResponse;
import com.gdsc.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //회원가입
    @PostMapping("/")
    public ResponseEntity<User> signup(@RequestBody UserDto userDto){
        User signup = userService.signup(userDto);

        return new ResponseEntity<>(signup, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> signIn(@RequestBody UserDto userDto){
        return  new ResponseEntity<>(userService.signIn(userDto),HttpStatus.OK);
    }
}
