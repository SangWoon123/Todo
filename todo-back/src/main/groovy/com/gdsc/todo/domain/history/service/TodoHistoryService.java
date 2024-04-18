package com.gdsc.todo.domain.history.service;

import com.gdsc.todo.domain.history.domain.TodoHistory;
import com.gdsc.todo.domain.history.dto.TodoHistoryFeedRequest;
import com.gdsc.todo.domain.history.dto.TodoHistoryResponse;
import com.gdsc.todo.domain.history.repository.TodoHistoryRepository;
import com.gdsc.todo.domain.task.domain.Todo;
import com.gdsc.todo.domain.task.dto.TodoResponse;
import com.gdsc.todo.domain.task.service.TodoService;
import com.gdsc.todo.domain.user.domain.User;
import com.gdsc.todo.domain.user.service.UserService;
import com.gdsc.todo.global.details.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoHistoryService {

    private final TodoHistoryRepository historyRepository;
    private final TodoService todoService;
    private final UserService userService;

    public List<TodoHistoryResponse> getRecentHistory(CustomUser userDto) {
        User user = userService.findByEmail(userDto.getEmail());


        List<TodoHistory> recentHistory = historyRepository.findTop7ByUserOrderByDayDesc(user);


        return recentHistory.stream()
                .map(history -> {
                            List<TodoResponse> todos = todoService.findByTodayAndUser(history.getDay(), user)
                                    .stream()
                                    .map(TodoResponse::from)
                                    .collect(Collectors.toList());
                            return TodoHistoryResponse.from(history, todos);
                        }
                ).collect(Collectors.toList());
    }

    @Transactional
    public TodoHistoryFeedRequest postFeed(CustomUser userDto, TodoHistoryFeedRequest todoHistoryFeedRequest, Long historyId) {
        User user = userService.findByEmail(userDto.getEmail());

        TodoHistory history = historyRepository.findByUserAndId(user, historyId)
                .orElseThrow(() -> new UsernameNotFoundException("history not found"));
        history.updateFeed(todoHistoryFeedRequest.getFeedback(), todoHistoryFeedRequest.getEmotion());


        return TodoHistoryFeedRequest.from(history);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void recordYesterdayTasks() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<User> allUsers = userService.findAll();

        for (User user : allUsers) {
            List<Todo> yesterdayTasks = todoService.findByTodayAndUser(yesterday, user);

            if (!yesterdayTasks.isEmpty()) {
                TodoHistory history = TodoHistory.builder()
                        .day(yesterday)
                        .total((long) yesterdayTasks.size())
                        .isComplete(yesterdayTasks.stream().filter(Todo::isDone).count())
                        .user(user)
                        .todos(yesterdayTasks)
                        .build();
                historyRepository.save(history);

                for (Todo todo : yesterdayTasks) {
                    todo.connectHistory(history);
                }
            }


        }
    }


}
