package com.gdsc.todo.domain.user.repository;

import com.gdsc.todo.domain.user.domain.SocialType;
import com.gdsc.todo.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);

    Boolean existsByEmail(String email);
}
