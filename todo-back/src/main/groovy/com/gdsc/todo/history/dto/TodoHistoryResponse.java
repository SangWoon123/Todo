package com.gdsc.todo.history.dto;

import com.gdsc.todo.history.domain.TodoHistory;
import lombok.*;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Builder
public class TodoHistoryResponse {
    private LocalDate day;
    private Long total;
    private Long complete;

    public static TodoHistoryResponse from(TodoHistory history){
        return TodoHistoryResponse.builder()
                .day(history.getDay())
                .total(history.getTotal())
                .complete(history.getComplete())
                .build();
    }
}
