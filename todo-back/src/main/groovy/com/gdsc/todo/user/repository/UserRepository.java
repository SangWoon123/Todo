package com.gdsc.todo.user.repository;

import com.gdsc.todo.user.dao.SocialType;
import com.gdsc.todo.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}
