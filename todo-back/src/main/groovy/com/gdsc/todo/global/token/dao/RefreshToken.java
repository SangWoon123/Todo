package com.gdsc.todo.global.token.dao;

import com.gdsc.todo.user.dao.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public void update(String refreshToken){
        this.refreshToken=refreshToken;
    }
}
