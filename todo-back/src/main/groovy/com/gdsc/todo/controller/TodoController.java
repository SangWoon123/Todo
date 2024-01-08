package com.gdsc.todo.controller;

import com.gdsc.todo.domain.Todo;
import com.gdsc.todo.dto.request.TodoRequest;
import com.gdsc.todo.dto.response.TodoResponse;
import com.gdsc.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TodoController {

    private final TodoService todoService;

    // 생성
    @PostMapping
    public ResponseEntity<TodoResponse> createTask(@RequestBody TodoRequest create){
        TodoResponse createdTask = todoService.createTask(create);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    // 읽기
//    @GetMapping("/{taskId}")
//    public ResponseEntity<TodoResponse> getAllTasks(@PathVariable Long ){
//        todoService.getAllTasks();
//    }

    // 수정
    @PatchMapping("/{taskId}")
    public ResponseEntity<TodoResponse> updateTask(@PathVariable("taskId") Long taskId, @RequestBody TodoRequest update){
        TodoResponse updateTask = todoService.updateTask(taskId, update);
        return new ResponseEntity<>(updateTask,HttpStatus.ACCEPTED);
    }

    // 삭제
    
}
