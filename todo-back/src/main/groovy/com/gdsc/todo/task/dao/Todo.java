package com.gdsc.todo.task.dao;

import com.gdsc.todo.history.domain.TodoHistory;
import com.gdsc.todo.user.dao.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@Table(name = "Task")
public class Todo{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String description;

    private boolean done;

    private LocalDateTime completeTime;

    private LocalDate today;

    @ManyToOne
    @JoinColumn(name = "history_id")
    private TodoHistory history;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void update(Optional<String> content, Optional<String> description){
        content.ifPresent(value -> this.content=value);
        description.ifPresent(value->this.description=value);
    }

    public void toggleComplete() {
        if (done) {
            this.done = false;
            this.completeTime = null;
        } else {
            this.done = true;
            this.completeTime = LocalDateTime.now();
        }
    }
    public void connectHistory(TodoHistory history) {
        this.history = history;
    }
}
