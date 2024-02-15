package com.gdsc.todo.history.domain;

import com.gdsc.todo.task.dao.Todo;
import com.gdsc.todo.history.repository.TodoHistoryRepository;
import com.gdsc.todo.user.dao.User;
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
public class TodoHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate day;

    private Long total;

    private Long complete;

    @OneToMany(mappedBy = "history")
    private List<Todo> todos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public void addTodo(Todo todo) {
        this.todos.add(todo);
        this.total++; // 추후 변경
    }

    public static TodoHistory of(User user,LocalDate day, TodoHistoryRepository historyRepository){
        return historyRepository.findByDayAndUser(day,user)
                .orElseGet(()-> TodoHistory.builder()
                        .total(0L)
                        .complete(0L)
                        .day(day)
                        .user(user)
                        .todos(new ArrayList<>()).build());
    }
}
