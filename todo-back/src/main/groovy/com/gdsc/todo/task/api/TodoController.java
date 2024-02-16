package com.gdsc.todo.task.api;

import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.task.dto.TodoRequest;
import com.gdsc.todo.task.dto.TodoResponse;
import com.gdsc.todo.task.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Todo",description = "Todo목록 작성")
@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TodoController {

    private final TodoService todoService;

    @Operation(summary = "Todo 목록 생성")
    @PostMapping
    public ResponseEntity<TodoResponse> createTask(@AuthenticationPrincipal CustomUser userDto, @RequestBody TodoRequest create){
        TodoResponse createdTask = todoService.createTask(userDto,create);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Operation(summary = "Todo 목록 읽기")
    @GetMapping("/today")
    public ResponseEntity<List<TodoResponse>> getAllTasks(@AuthenticationPrincipal CustomUser userDto){
        List<TodoResponse> todayTasks=todoService.getTodayTask(userDto);
        return new ResponseEntity<>(todayTasks,HttpStatus.OK);
    }


    @Operation(summary = "Todo 목록 수정")
    @PatchMapping("/{taskId}")
    public ResponseEntity<TodoResponse> updateTask(@AuthenticationPrincipal CustomUser userDto,@PathVariable("taskId") Long taskId, @RequestBody TodoRequest update){
        TodoResponse updateTask = todoService.updateTask(userDto,taskId, update);
        return new ResponseEntity<>(updateTask,HttpStatus.ACCEPTED);
    }
    @Operation(summary = "Todo 목록 삭제")
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@AuthenticationPrincipal CustomUser userDto,@PathVariable("taskId") Long taskId){
        todoService.deleteTask(userDto,taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
