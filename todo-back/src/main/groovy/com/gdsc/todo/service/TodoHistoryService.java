package com.gdsc.todo.service;

import com.gdsc.todo.domain.Todo;
import com.gdsc.todo.domain.TodoHistory;
import com.gdsc.todo.dto.response.TodoHistoryResponse;
import com.gdsc.todo.repository.TodoHistoryRepository;
import com.gdsc.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoHistoryService {

    private final TodoHistoryRepository historyRepository;
    private final TodoRepository todoRepository;

    public List<TodoHistoryResponse> getRecentHistory() {
        List<TodoHistory> recentHistory = historyRepository.findTop7ByOrderByDayDesc();

        return recentHistory.stream()
                .map(TodoHistoryResponse::from)
                .collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void recordYesterdayTasks() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<Todo> yesterdayTasks = todoRepository.findByToday(yesterday);

        TodoHistory history = TodoHistory.of(yesterday, historyRepository);
        yesterdayTasks.forEach(history::addTodo);

        historyRepository.save(history);
    }
}
