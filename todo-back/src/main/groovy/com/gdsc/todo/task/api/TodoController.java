package com.gdsc.todo.task.api;

import com.gdsc.todo.task.dto.TodoRequest;
import com.gdsc.todo.task.dto.TodoResponse;
import com.gdsc.todo.task.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/today")
    public ResponseEntity<List<TodoResponse>> getAllTasks(){
        List<TodoResponse> todayTasks=todoService.getTodayTask();
        return new ResponseEntity<>(todayTasks,HttpStatus.OK);
    }


    // 수정
    @PatchMapping("/{taskId}")
    public ResponseEntity<TodoResponse> updateTask(@PathVariable("taskId") Long taskId, @RequestBody TodoRequest update){
        TodoResponse updateTask = todoService.updateTask(taskId, update);
        return new ResponseEntity<>(updateTask,HttpStatus.ACCEPTED);
    }

    // 삭제
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") Long taskId){
        todoService.delete(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
