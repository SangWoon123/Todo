package com.gdsc.todo.service;

import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.domain.history.domain.Emotion;
import com.gdsc.todo.domain.history.domain.TodoHistory;
import com.gdsc.todo.domain.history.repository.TodoHistoryRepository;
import com.gdsc.todo.domain.history.service.TodoHistoryService;
import com.gdsc.todo.domain.task.domain.Todo;
import com.gdsc.todo.domain.task.repository.TodoRepository;
import com.gdsc.todo.domain.user.domain.SocialType;
import com.gdsc.todo.domain.user.domain.User;
import com.gdsc.todo.domain.user.repository.UserRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
@Transactional
class TodoHistoryServiceTest {

    @Autowired
    private TodoHistoryService historyService;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoHistoryRepository historyRepository;
    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private User testUser2;

    @BeforeEach
    public void setUp() {
        testUser = createUser("testEmail");
        testUser2 = createUser("testEmail2");
    }

    private User createUser(String email) {
        User user = User.builder()
                .username("test")
                .role(Role.GUEST)
                .email(email)
                .profileImage("testURL")
                .socialId("testSocial")
                .socialType(SocialType.NAVER)
                .build();
        return userRepository.save(user);
    }

    private TodoHistory createAndSaveTodoHistory(int dayOffset, long total, long complete) {
        TodoHistory history = TodoHistory.builder()
                .day(LocalDate.now().minusDays(dayOffset))
                .total(total)
                .isComplete(complete)
                .user(testUser)
                .build();
        historyRepository.save(history);
        return history;
    }

    private Todo createAndSabeTodo(String content, String description, User user) {
        Todo todo = Todo.builder()
                .today(LocalDate.now().minusDays(1))
                .content(content)
                .description(description)
                .done(false)
                .completeTime(LocalDateTime.now().minusDays(1))
                .user(user)
                .build();
        return todoRepository.save(todo);
    }


    @Test
    @DisplayName("최근 7개 히스토리 가져오기")
    public void getRecentHistory() {
        //given
        IntStream.range(0, 10).forEach(i -> {
            createAndSaveTodoHistory(i, 10L, 5L);
        });
        //when
        List<TodoHistory> recentHistory = historyRepository.findTop7ByUserOrderByDayDesc(testUser);
        //then
        Assertions.assertEquals(7, recentHistory.size());
    }

    @Test
    @DisplayName("자정이후 기록되는 todo목록")
    public void recordYesterdayTasksTest() {
        //given
        createAndSabeTodo("운동하기", "오후 2시 한강공원", testUser);

        //when
        historyService.recordYesterdayTasks();
        TodoHistory todoHistory = historyRepository.findByUser(testUser)
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        //then
        assertThat(todoHistory.getTodos()).hasSize(1);
        assertThat(todoHistory.getTodos().get(0).getContent()).isEqualTo("운동하기");
    }

    @Test
    @DisplayName("자정이후 기록되는 todo목록 (유저여러명일때 자신의 기록만 가져오는지)")
    public void recordYesterdayTasksTest2() {
        //given
        createAndSabeTodo("운동하기", "오후 2시 한강공원", testUser);
        createAndSabeTodo("장보기", "오후 5시 롯데마트", testUser2);

        historyService.recordYesterdayTasks();

        TodoHistory todoHistory = historyRepository.findByUser(testUser)
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));

        assertThat(todoHistory.getTodos()).hasSize(1);
    }

    @Test
    @DisplayName("피드 저장")
    public void postFeedTest(){
        testUser=createUser("testEmail");
        createAndSabeTodo("테스트","테스트",testUser);

        historyService.recordYesterdayTasks();

        TodoHistory todoHistory = historyRepository.findByUser(testUser)
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));

        todoHistory.updateFeed("피드를 작성", Emotion.HAPPY);

        Assertions.assertEquals(todoHistory.getFeedback(),"피드를 작성");
    }

    @After("")
    public void tearDown() {
        todoRepository.deleteAll();
        userRepository.deleteAll();
    }

}