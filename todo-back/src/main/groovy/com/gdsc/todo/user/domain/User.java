package com.gdsc.todo.user.domain;

import com.gdsc.todo.global.audit.BaseEntityTIme;
import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.history.domain.TodoHistory;
import com.gdsc.todo.task.domain.Todo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USER")
public class User extends BaseEntityTIme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "social_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TodoHistory> todoHistories = new ArrayList<>();
    @Builder
    public User(String username, String email, Role role, SocialType socialType, String socialId, String profileImage, List<Todo> todos, List<TodoHistory> todoHistories) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.profileImage = profileImage;
        this.todos = todos;
        this.todoHistories = todoHistories;
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }

    public void addTodoHistory(TodoHistory history) {
        this.todoHistories.add(history);
    }

}
