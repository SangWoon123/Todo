package com.gdsc.todo.repository;

import com.gdsc.todo.domain.TodoHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TodoHistoryRepository extends JpaRepository<TodoHistory,Long> {
    List<TodoHistory> findTop7ByOrderByDayDesc();
    Optional<TodoHistory> findByDay(LocalDate day);
}
