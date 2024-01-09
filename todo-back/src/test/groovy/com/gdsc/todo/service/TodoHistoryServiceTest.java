package com.gdsc.todo.service;

import com.gdsc.todo.domain.Todo;
import com.gdsc.todo.domain.TodoHistory;
import com.gdsc.todo.dto.response.TodoHistoryResponse;
import com.gdsc.todo.repository.TodoHistoryRepository;
import com.gdsc.todo.repository.TodoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class TodoHistoryServiceTest {

    @Autowired
    private TodoHistoryService historyService;
    @Autowired
    private TodoService todoService;
    @Autowired
    TodoRepository todoRepository;
    @Autowired
    TodoHistoryRepository historyRepository;

    private void createAndSaveTodoHistory(int i) {
        TodoHistory history = TodoHistory.builder()
                .day(LocalDate.now().minusDays(i))
                .total(10L)
                .complete(5L)
                .todos(new ArrayList<>())
                .build();

        historyRepository.save(history);
    }

    @Test
    @DisplayName("최근 7개 히스토리 가져오기")
    public void getRecentHistory() {
        //given
        IntStream.range(0, 10).forEach(i -> createAndSaveTodoHistory(i));

        //when
        List<TodoHistory> recentHistory = historyRepository.findTop7ByOrderByDayDesc();

        //then
        Assertions.assertEquals(7, recentHistory.size());
    }

    @Test
    @DisplayName("자정이후 기록되는 todo목록")
    public void recordYesterdayTasksTest() {
        //given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Todo todo1 = Todo.builder()
                .today(yesterday)
                .content("운동하기")
                .description("오후 2시 한강공원")
                .done(false)
                .completeTime(LocalDateTime.now().minusDays(1))
                .build();
        todoRepository.save(todo1);

        //when
        historyService.recordYesterdayTasks();

        //then
        TodoHistory history = historyRepository.findByDay(yesterday).get();
        Assertions.assertEquals(1, history.getTodos().size());
    }

}