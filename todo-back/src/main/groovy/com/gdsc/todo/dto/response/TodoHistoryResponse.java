package com.gdsc.todo.dto.response;

import com.gdsc.todo.domain.TodoHistory;
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
