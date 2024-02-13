package com.gdsc.todo.task.repository;

import com.gdsc.todo.task.dao.Todo;
import com.gdsc.todo.user.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTodayAndUser(LocalDate date,User user);
    List<Todo> findByToday(LocalDate date);
    List<Todo> findByUser(User user);
}
