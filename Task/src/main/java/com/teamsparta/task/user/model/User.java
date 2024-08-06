package com.teamsparta.task.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
//카카오와 통합
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname; // 별명

    @Column(nullable = false, unique = true)
    private String username; // 사용자 이름 (username)

    @Column
    private String password; // 비밀번호 (password), 소셜 사용자의 경우 null일 수 있음

//    @Column(nullable = false) 나중에 관리자 추가할거면 주석 해제
//    @Enumerated(EnumType.STRING)
//    private UserRoleEnum role; // 권한 (USER 또는 ADMIN)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성일

    @Column(unique = true) // 카카오 ID 추가, 이건 카카오 ID는 유일하다는거
    private Long kakaoId; // 카카오 사용자 ID 즉, 소셜 사용자의 경우에만 사용

    @Column(nullable = true)
    private String email; // 소셜로그인이 이메일 쓰니 추가

    //일반 유저로그인용
    public User(String nickname, String username, String password, String email, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createdAt = createdAt;
    }
    // 매개변수를 받는 생성자 추가, String email도 추가
    //카카오 로그인용
    public User(String nickname, String username, String password,String email, LocalDateTime createdAt, Long kakaoId) {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.email = email; // 소셜로그인이 이메일 쓰니 추가
        this.kakaoId = kakaoId;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        return this;
    }
}


