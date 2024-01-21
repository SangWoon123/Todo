package com.gdsc.todo.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoRequest {

    private String content;
    private String description;
    private boolean done;
    private LocalDateTime completeTime;
}
