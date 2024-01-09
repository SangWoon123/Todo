package com.gdsc.todo.service;

import com.gdsc.todo.domain.Todo;
import com.gdsc.todo.domain.TodoHistory;
import com.gdsc.todo.dto.request.TodoRequest;
import com.gdsc.todo.dto.response.TodoResponse;
import com.gdsc.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoHistoryService historyService;

    public TodoResponse createTask(TodoRequest create) {
        Todo task = Todo.builder()
                .content(create.getContent())
                .description(create.getDescription())
                .today(LocalDate.now())
                .done(false)
                .build();
        Todo save = todoRepository.save(task);

        TodoResponse newTask = TodoResponse.from(save);
        return newTask;
    }

    public List<TodoResponse> getTodayTask(){
        LocalDate today=LocalDate.now();
        List<Todo> todayTasks = todoRepository.findByToday(today);

        return todayTasks.stream()
                .map(TodoResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponse updateTask(Long taskId,TodoRequest update) {
        Todo task = todoRepository.getById(taskId);
        task.update(Optional.ofNullable(update.getContent()), Optional.ofNullable(update.getDescription()));

        // domain -> dto
        TodoResponse updateTask = TodoResponse.from(task);

        return updateTask;
    }

    public void delete(Long taskId) {
        todoRepository.deleteById(taskId);
    }
}
