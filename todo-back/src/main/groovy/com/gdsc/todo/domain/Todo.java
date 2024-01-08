package com.gdsc.todo.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@DynamicUpdate // 변경한 필드만 대응
@Table(name = "Task")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String description;

    private boolean done;

    private LocalDateTime completeTime;

    public void update(String content,String description){
        if(content !=null){
            this.content=content;
        }
        if(description !=null){
            this.description=description;
        }
    }
}
