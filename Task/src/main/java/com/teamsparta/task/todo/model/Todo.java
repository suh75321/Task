package com.teamsparta.task.todo.model;
//lombok를 통해 Getter,Setter,기본 생성자를 자동 생성
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String username;

    private String password;

    private LocalDateTime createdAt;

    @Builder
    public Todo(String title, String content, String username, String password, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public void update(String title, String content, String username) {
        this.title = title;
        this.content = content;
        this.username = username;
    }
}

