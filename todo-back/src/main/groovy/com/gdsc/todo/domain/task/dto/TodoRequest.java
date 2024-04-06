package com.gdsc.todo.domain.task.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoRequest {

    private String content;
    private String description;
    private boolean done;
    private LocalDateTime completeTime;
}
