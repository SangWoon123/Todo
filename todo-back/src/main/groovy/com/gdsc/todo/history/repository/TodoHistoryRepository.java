package com.gdsc.todo.history.repository;

import com.gdsc.todo.history.domain.TodoHistory;
import com.gdsc.todo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoHistoryRepository extends JpaRepository<TodoHistory,Long> {
    List<TodoHistory> findTop7ByUserOrderByDayDesc(User user);
    Optional<TodoHistory> findByDayAndUser(LocalDate day,User user);
    Optional<TodoHistory> findByUser(User user);

    Optional<TodoHistory> findByUserAndId(User user,Long id);
}
