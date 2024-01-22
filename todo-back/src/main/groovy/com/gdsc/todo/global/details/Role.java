package com.gdsc.todo.global.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {

    ADMIN("ROLE_ADMIN"),
    GUEST("ROLE_GUEST"),
    USER("ROLE_USER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getAuthority() {
        return value;
    }
}
