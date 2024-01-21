package com.gdsc.todo.task;

import com.gdsc.todo.task.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByToday(LocalDate date);
}
