package com.gdsc.todo.history.service;

import com.gdsc.todo.task.dao.Todo;
import com.gdsc.todo.history.domain.TodoHistory;
import com.gdsc.todo.history.repository.TodoHistoryRepository;
import com.gdsc.todo.history.dto.TodoHistoryResponse;
import com.gdsc.todo.task.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
