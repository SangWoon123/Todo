package com.gdsc.todo.user.dto;

import com.gdsc.todo.global.details.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Data
public class OAuth2Response {
    private Long id;

    private String email;

    private String nickname;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public OAuth2Response(Long id, String email,
                          String nickname, String imageUrl,
                          Role role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.imageUrl = imageUrl;
        this.role = role;
    }
}
