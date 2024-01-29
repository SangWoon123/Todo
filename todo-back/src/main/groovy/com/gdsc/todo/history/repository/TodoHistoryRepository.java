package com.gdsc.todo.history.repository;

import com.gdsc.todo.history.domain.TodoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoHistoryRepository extends JpaRepository<TodoHistory,Long> {
    List<TodoHistory> findTop7ByOrderByDayDesc();
    Optional<TodoHistory> findByDay(LocalDate day);
}
