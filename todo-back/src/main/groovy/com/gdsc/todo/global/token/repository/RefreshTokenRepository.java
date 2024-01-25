package com.gdsc.todo.global.token.repository;

import com.gdsc.todo.global.token.dao.RefreshToken;
import com.gdsc.todo.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Ref;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByUser(User user);
}
