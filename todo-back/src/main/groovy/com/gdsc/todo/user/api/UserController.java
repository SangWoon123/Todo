package com.gdsc.todo.user.api;

import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.user.dao.User;
import com.gdsc.todo.user.dto.OAuth2Response;
import com.gdsc.todo.user.dto.UserResponse;
import com.gdsc.todo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserController {
    private final UserService userService;

    @GetMapping("/test")
    public ResponseEntity<?> oAuthTest(@AuthenticationPrincipal CustomUser principal){
        return new ResponseEntity<>(principal,HttpStatus.OK);
    }

    @GetMapping("/user")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUser username = (CustomUser) authentication.getPrincipal(); // 현재 사용자의 username을 가져옵니다.



        return "현재 로그인한 사용자: " + username.getEmail();
    }

}
