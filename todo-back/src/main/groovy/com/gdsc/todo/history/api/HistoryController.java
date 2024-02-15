package com.gdsc.todo.history.api;

import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.history.dto.TodoHistoryResponse;
import com.gdsc.todo.history.service.TodoHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final TodoHistoryService historyService;

    @GetMapping()
    public ResponseEntity<List<TodoHistoryResponse>> getRecentHistory(@AuthenticationPrincipal CustomUser userDto){
        List<TodoHistoryResponse> recentHistory = historyService.getRecentHistory(userDto);
        return new ResponseEntity<>(recentHistory,HttpStatus.OK);
    }
}
