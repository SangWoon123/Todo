package com.gdsc.todo.task.repository;

import com.gdsc.todo.task.domain.Todo;
import com.gdsc.todo.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByTodayAndUser(LocalDate date,User user);
    List<Todo> findByToday(LocalDate date);
    List<Todo> findByUser(User user);
//    @Query("SELECT t FROM Todo t WHERE t.user = :user AND t.today = :today")
//    List<Todo> findTodosByUserAndToday(@Param("user") User user, @Param("today") LocalDate today);
}
