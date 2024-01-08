package com.gdsc.todo.dto.response;

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

}
