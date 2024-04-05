package com.gdsc.todo.user.domain;

import com.gdsc.todo.global.audit.BaseEntityTIme;
import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.global.token.dao.RefreshToken;
import com.gdsc.todo.history.domain.TodoHistory;
import com.gdsc.todo.task.domain.Todo;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntityTIme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String profileImage;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;

    @OneToMany(mappedBy = "user")
    private List<Todo> todos=new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TodoHistory> todoHistories=new ArrayList<>();

    @Builder
    public User(String username, String email, Role role, SocialType socialType, String socialId, String profileImage, RefreshToken refreshToken, List<Todo> todos) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.socialType = socialType;
        this.socialId = socialId;
        this.profileImage = profileImage;
        this.refreshToken = refreshToken;
        this.todos = new ArrayList<>();
        this.todoHistories=new ArrayList<>();
    }

    public void addTodo(Todo todo) {
        this.todos.add(todo);
    }
    public void addTodoHistory(TodoHistory history) {
        this.todoHistories.add(history);
    }

}
