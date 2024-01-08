package com.gdsc.todo.dto.response;

import com.gdsc.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TodoResponse {

    private Long id;
    private String content;
    private String description;
    private LocalDateTime completeTime;

    public static TodoResponse from(Todo todo) {
        return TodoResponse.builder()
                .id(todo.getId())
                .content(todo.getContent())
                .description(todo.getDescription())
                .completeTime(todo.getCompleteTime())
                .build();
    }


}
