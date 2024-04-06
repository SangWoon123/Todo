package com.gdsc.todo.domain.history.dto;

import com.gdsc.todo.domain.history.domain.Emotion;
import com.gdsc.todo.domain.history.domain.TodoHistory;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoHistoryFeedRequest {
    private String feedback;
    private Emotion emotion;

    public static TodoHistoryFeedRequest from(TodoHistory history){
        return TodoHistoryFeedRequest.builder()
                .feedback(history.getFeedback())
                .emotion(history.getEmotion())
                .build();
    }
}
