package com.teamsparta.task.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")//테이블 이름은 users로 user이 아니다. 유저로 하면 오류가 나기 때문이다.
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname; // 별명

    @Column(nullable = false, unique = true)
    private String username; // 사용자 이름 (username)

    @Column(nullable = false)
    private String password; // 비밀번호 (password)

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role; // 권한 (USER 또는 ADMIN)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성일

    // 매개변수를 받는 생성자 추가
    public User(String nickname, String username, String password, UserRoleEnum  role, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.role = role;
        this.createdAt = createdAt;
    }

    // 생성일 자동 설정을 위한 메서드
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}


