package com.gdsc.todo.domain.history.api;

import com.gdsc.todo.domain.history.dto.TodoHistoryFeedRequest;
import com.gdsc.todo.domain.history.dto.TodoHistoryResponse;
import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.domain.history.service.TodoHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "TodoHistory", description = "기록한 Todo리스트 저장")
@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
public class HistoryController {

    private final TodoHistoryService historyService;
    @Operation(summary = "최근 저장한 7일의 Todo리스트 정보 목록 가져오기")
    @GetMapping()
    public ApiWrapper<List<TodoHistoryResponse>> getRecentHistory(@AuthenticationPrincipal CustomUser userDto){
        List<TodoHistoryResponse> recentHistory = historyService.getRecentHistory(userDto);
        return new ApiWrapper<>(recentHistory, HttpStatus.OK);
    }

    @Operation(summary = "피드백 작성")
    @PostMapping("/feed/{historyId}")
    public ResponseEntity<TodoHistoryFeedRequest> postFeed(@AuthenticationPrincipal CustomUser userDto, @RequestBody TodoHistoryFeedRequest todoHistoryFeedRequest, @PathVariable("historyId")Long historyId){
        return new ResponseEntity<>(historyService.postFeed(userDto, todoHistoryFeedRequest,historyId), HttpStatus.OK);
    }
}
