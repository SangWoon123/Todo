package com.gdsc.todo.domain.task.dto;

import com.gdsc.todo.domain.task.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TodoResponse {

    private Long id;
    private String content;
    private String description;
    private LocalDate today;
    private boolean done;
    private LocalDateTime completeTime;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .today(todo.getToday())
                .description(todo.getDescription())
                .done(todo.isDone())
                .completeTime(todo.getCompleteTime())
                .build();
    }


}
