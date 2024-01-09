package com.gdsc.todo.controller;

import com.gdsc.todo.dto.response.TodoHistoryResponse;
import com.gdsc.todo.service.TodoHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<TodoHistoryResponse>> getRecentHistory(){
        List<TodoHistoryResponse> recentHistory = historyService.getRecentHistory();
        return new ResponseEntity<>(recentHistory,HttpStatus.OK);
    }
}
