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
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
public class TodoHistory extends BaseEntityTIme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate day;

    private Long total;

    private Long complete;

    private String feedback;

    @Enumerated(value = EnumType.STRING)
    private Emotion emotion;

    @OneToMany(mappedBy = "history")
    private List<Todo> todos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public void addTodo(Todo todo) {
        this.todos.add(todo);
        this.total++; // 추후 변경
    }

    public static TodoHistory of(User user, LocalDate day, TodoHistoryRepository historyRepository) {
        return historyRepository.findByDayAndUser(day, user)
                .orElseGet(() -> TodoHistory.builder()
                        .total(0L)
                        .complete(0L)
                        .day(day)
                        .user(user)
                        .todos(new ArrayList<>()).build());
    }

    public void updateFeed(String feedback, Emotion emotion) {
        this.feedback = feedback;
        this.emotion = emotion;
    }
}
