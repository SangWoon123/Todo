package com.gdsc.todo.history.service;

import com.gdsc.todo.global.details.CustomUser;
import com.gdsc.todo.task.dao.Todo;
import com.gdsc.todo.history.domain.TodoHistory;
import com.gdsc.todo.history.repository.TodoHistoryRepository;
import com.gdsc.todo.history.dto.TodoHistoryResponse;
import com.gdsc.todo.task.dto.TodoResponse;
import com.gdsc.todo.task.repository.TodoRepository;
import com.gdsc.todo.user.dao.User;
import com.gdsc.todo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoHistoryService {

    private final TodoHistoryRepository historyRepository;
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public List<TodoHistoryResponse> getRecentHistory(CustomUser userDto) {
        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));


        List<TodoHistory> recentHistory = historyRepository.findTop7ByUserOrderByDayDesc(user);



        return recentHistory.stream()
                .map(history->{
                            List<TodoResponse> todos = todoRepository.findByTodayAndUser(history.getDay(),user)
                                    .stream()
                                    .map(TodoResponse::from)
                                    .collect(Collectors.toList());
                            return TodoHistoryResponse.from(history, todos);
                        }
                ).collect(Collectors.toList());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void recordYesterdayTasks() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        List<User> allUsers = userRepository.findAll();

        for(User user: allUsers){
            List<Todo> yesterdayTasks = todoRepository.findByTodayAndUser(yesterday,user);

            if(!yesterdayTasks.isEmpty()){
                TodoHistory history = TodoHistory.builder()
                        .day(yesterday)
                        .total((long) yesterdayTasks.size())
                        .complete(yesterdayTasks.stream().filter(Todo::isDone).count())
                        .user(user)
                        .todos(yesterdayTasks)
                        .build();
                historyRepository.save(history);
            }
        }
    }

}
