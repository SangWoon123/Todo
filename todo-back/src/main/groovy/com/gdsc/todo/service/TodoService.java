package com.gdsc.todo.service;

import com.gdsc.todo.domain.Todo;
import com.gdsc.todo.dto.request.TodoRequest;
import com.gdsc.todo.dto.response.TodoResponse;
import com.gdsc.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponse createTask(TodoRequest create) {
        Todo task = Todo.builder()
                .content(create.getContent())
                .description(create.getDescription())
                .completeTime(LocalDateTime.now())
                .done(false)
                .build();
        Todo save = todoRepository.save(task);

        TodoResponse newTask = TodoResponse.builder()
                .id(save.getId())
                .content(save.getContent())
                .description(save.getDescription())
                .completeTime(save.getCompleteTime())
                .build();
        return newTask;
    }

    @Transactional
    public TodoResponse updateTask(Long taskId,TodoRequest update) {
        Todo task = todoRepository.getById(taskId);
        task.update(update.getContent(), update.getDescription());

        TodoResponse updateTask = TodoResponse.builder()
                .id(task.getId())
                .content(task.getContent())
                .description(task.getDescription())
                .completeTime(task.getCompleteTime())
                .build();

        return updateTask;
    }

    public void delete(Long taskId) {
        todoRepository.deleteById(taskId);
    }
}
