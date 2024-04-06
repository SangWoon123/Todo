package com.gdsc.todo.service;

import com.gdsc.todo.global.details.Role;
import com.gdsc.todo.domain.task.domain.Todo;
import com.gdsc.todo.domain.task.repository.TodoRepository;
import com.gdsc.todo.domain.task.service.TodoService;
import com.gdsc.todo.domain.user.domain.SocialType;
import com.gdsc.todo.domain.user.domain.User;
import com.gdsc.todo.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
public class TodoServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private TodoService todoService;
    @Test
    @DisplayName("유저가 todo목록 생성")
    public void createTask(){
        //given(유저, todo생성)
        User user = User.builder()
                .username("test")
                .role(Role.GUEST)
                .email("test@naver.com")
                .profileImage("testURL")
                .socialId("naver")
                .socialType(SocialType.NAVER)
                .build();

        userRepository.save(user);

        Todo task = Todo.builder()
                .content("test")
                .description("test")
                .today(LocalDate.now())
                .done(false)
                .user(user)
                .build();

        todoRepository.save(task);

        user.addTodo(task);

        //when(유저 작성목록 가져오기)
        List<Todo> byUser = todoRepository.findByUser(user);

        //then(검증)
        Assertions.assertEquals(1,byUser.size());
    }

    @Test
    @DisplayName("Todo작성목록 읽기")
    public void read_task(){
        //given(유저, todo생성)
        User user = User.builder()
                .username("test")
                .role(Role.GUEST)
                .email("test@naver.com")
                .profileImage("testURL")
                .socialId("naver")
                .socialType(SocialType.NAVER)
                .build();

        userRepository.save(user);

        Todo task = Todo.builder()
                .content("test1")
                .description("test1")
                .today(LocalDate.now())
                .done(false)
                .user(user)
                .build();

        todoRepository.save(task);

        user.addTodo(task);

        User user2 = User.builder()
                .username("test2")
                .role(Role.GUEST)
                .email("test@naver.com")
                .profileImage("testURL")
                .socialId("naver")
                .socialType(SocialType.NAVER)
                .build();

        userRepository.save(user2);

        Todo task2 = Todo.builder()
                .content("test2")
                .description("test2")
                .today(LocalDate.now())
                .done(false)
                .user(user2)
                .build();

        todoRepository.save(task2);

        user2.addTodo(task2);

        //when(유저 작성목록 가져오기)
        List<Todo> byUser = todoRepository.findByUser(user);
        List<Todo> byUser2 = todoRepository.findByUser(user2);

        //then(검증)
        Assertions.assertEquals("test1",byUser.get(0).getDescription());
        Assertions.assertEquals("test2",byUser2.get(0).getDescription());
    }

    @Test
    @DisplayName("Todo 목록 수정")
    public void update_task(){
        //given(유저, todo생성)
        User user = User.builder()
                .username("test")
                .role(Role.GUEST)
                .email("test@naver.com")
                .profileImage("testURL")
                .socialId("naver")
                .socialType(SocialType.NAVER)
                .build();

        userRepository.save(user);

        Todo task = Todo.builder()
                .content("test")
                .description("test")
                .today(LocalDate.now())
                .done(false)
                .user(user)
                .build();

        Todo saveTask = todoRepository.save(task);

        user.addTodo(task);

        String expectedContent = "컨텐츠 수정";
        String expectedDescription = "설명 수정";
        //when(수정)
        saveTask.update(Optional.of(expectedContent),Optional.of(expectedDescription));

        //when(유저 작성목록 가져오기)
        List<Todo> byUser = todoRepository.findByUser(user);

        Assertions.assertEquals(byUser.get(0).getContent(),expectedContent);
        Assertions.assertEquals(byUser.get(0).getDescription(),expectedDescription);
    }

    @Test
    @DisplayName("Todo목록 삭제")
    public void delete_task(){
        //given(유저, todo생성)
        User user = User.builder()
                .username("test")
                .role(Role.GUEST)
                .email("test@naver.com")
                .profileImage("testURL")
                .socialId("naver")
                .socialType(SocialType.NAVER)
                .build();

        userRepository.save(user);

        Todo task = Todo.builder()
                .content("test")
                .description("test")
                .today(LocalDate.now())
                .done(false)
                .user(user)
                .build();

        Todo saveTask = todoRepository.save(task);

        user.addTodo(task);

        //when(삭제)
        todoRepository.delete(saveTask);

        List<Todo> all = todoRepository.findAll();
        Assertions.assertEquals(0,all.size());
    }

}
