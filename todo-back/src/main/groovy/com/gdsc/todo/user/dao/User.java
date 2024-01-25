package com.gdsc.todo.user.dao;

import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.global.token.dao.RefreshToken;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id")
    private String username;

    @Column(name = "login_password")
    private String password;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    private String socialId;

    private String imageUrl;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;
}
