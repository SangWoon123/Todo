package com.gdsc.todo.domain.task.service;

import com.gdsc.todo.domain.task.domain.Todo;
import com.gdsc.todo.domain.task.repository.TodoRepository;
import com.gdsc.todo.domain.user.repository.UserRepository;
import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.domain.history.service.TodoHistoryService;
import com.gdsc.todo.domain.task.dto.TodoRequest;
import com.gdsc.todo.domain.task.dto.TodoResponse;
import com.gdsc.todo.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final TodoHistoryService historyService;
    private final UserRepository userRepository;

    public TodoResponse createTask(CustomUser userDto, TodoRequest create) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Todo task = Todo.builder()
                .content(create.getContent())
                .description(create.getDescription())
                .today(LocalDate.now())
                .done(false)
                .user(user)
                .build();
        Todo save = todoRepository.save(task);

        TodoResponse newTask = TodoResponse.from(save);
        return newTask;
    }

    public List<TodoResponse> getTodayTask(CustomUser userDto) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        LocalDate today = LocalDate.now();
        List<Todo> todayTasks = todoRepository.findByTodayAndUser(today, user);

        return todayTasks.stream()
                .map(TodoResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoResponse updateTask(CustomUser userDto, Long taskId, TodoRequest update) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Todo task = todoRepository.getById(taskId);

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not the owner of this task");
        }

        task.update(Optional.ofNullable(update.getContent()), Optional.ofNullable(update.getDescription()));

        // domain -> dto
        TodoResponse updateTask = TodoResponse.from(task);

        return updateTask;
    }

    public void deleteTask(CustomUser customUser, Long taskId) {
        User user = userRepository.findByEmail(customUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Todo task = todoRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not the owner of this task");
        }

        todoRepository.deleteById(taskId);
    }

    @Transactional
    public TodoResponse completeTask(CustomUser customUser, Long taskId) {
        User user = userRepository.findByEmail(customUser.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Todo task = todoRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (!task.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not the owner of this task");
        }

        task.toggleComplete();

        TodoResponse updateTask = TodoResponse.from(task);

        return updateTask;
    }
}
