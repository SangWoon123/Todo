package com.gdsc.todo.domain.task.domain;

import com.gdsc.todo.domain.history.domain.TodoHistory;
import com.gdsc.todo.global.audit.BaseEntityTIme;
import com.gdsc.todo.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TODO")
public class Todo extends BaseEntityTIme {

    @Id
    @Column(name = "todo_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "description")
    private String description;

    @Column(name = "done", nullable = false)
    private boolean done;

    @Column(name = "complete_time")
    private LocalDateTime completeTime;

    @Column(name = "today", nullable = false)
    private LocalDate today;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id")
    private TodoHistory history;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Todo(String content, String description, boolean done, LocalDateTime completeTime, LocalDate today, TodoHistory history, User user) {
        this.content = content;
        this.description = description;
        this.done = done;
        this.completeTime = completeTime;
        this.today = today;
        this.history = history;
        this.user = user;
    }

    public void update(Optional<String> content, Optional<String> description) {
        content.ifPresent(value -> this.content = value);
        description.ifPresent(value -> this.description = value);
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
