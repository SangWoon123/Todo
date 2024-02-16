package com.gdsc.todo.user.api;

import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "User", description = "회원 API 문서")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserController {
    @Operation(summary = "회원정보 확인")
    @GetMapping()
    public ResponseEntity<?> oAuthTest(@AuthenticationPrincipal CustomUser principal){
        return new ResponseEntity<>(principal,HttpStatus.OK);
    }

}
