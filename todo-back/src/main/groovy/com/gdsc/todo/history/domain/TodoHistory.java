package com.gdsc.todo.history.domain;

import com.gdsc.todo.global.audit.BaseEntityTIme;
import com.gdsc.todo.history.repository.TodoHistoryRepository;
import com.gdsc.todo.task.domain.Todo;
import com.gdsc.todo.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "TODOHISTORY")
public class TodoHistory extends BaseEntityTIme {

    @Id
    @Column(name = "todo_history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Column(name = "total")
    private Long total;

    @Column(name = "complete", nullable = false)
    private Long isComplete;

    @Column(name = "feed_back")
    private String feedback;

    @Column(name = "emotion")
    @Enumerated(value = EnumType.STRING)
    private Emotion emotion;

    @OneToMany(mappedBy = "history", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public TodoHistory(LocalDate day, Long total, Long isComplete, String feedback, Emotion emotion, List<Todo> todos, User user) {
        this.day = day;
        this.total = total;
        this.isComplete = isComplete;
        this.feedback = feedback;
        this.emotion = emotion;
        this.todos = todos;
        this.user = user;
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
        this.total++; // 추후 변경
    }

    public static TodoHistory of(User user, LocalDate day, TodoHistoryRepository historyRepository) {
        return historyRepository.findByDayAndUser(day, user)
                .orElseGet(() -> TodoHistory.builder()
                        .total(0L)
                        .isComplete(0L)
                        .day(day)
                        .user(user)
                        .todos(new ArrayList<>()).build());
    }

    public void updateFeed(String feedback, Emotion emotion) {
        this.feedback = feedback;
        this.emotion = emotion;
    }
}
