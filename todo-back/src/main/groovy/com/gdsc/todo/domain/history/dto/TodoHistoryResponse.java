package com.gdsc.todo.domain.history.dto;

import com.gdsc.todo.domain.history.domain.Emotion;
import com.gdsc.todo.domain.history.domain.TodoHistory;
import com.gdsc.todo.domain.task.dto.TodoResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class TodoHistoryResponse {
    private Long id;
    private LocalDate day;
    private Long total;
    private Long complete;
    private String feedback;
    private Emotion emotion;
    private List<TodoResponse> todos;

    public static TodoHistoryResponse from(TodoHistory history, List<TodoResponse> todos){
        return TodoHistoryResponse.builder()
                .id(history.getId())
                .day(history.getDay())
                .total(history.getTotal())
                .feedback(history.getFeedback())
                .emotion(history.getEmotion())
                .todos(todos)
                .complete(history.getIsComplete())
                .build();
    }
}
