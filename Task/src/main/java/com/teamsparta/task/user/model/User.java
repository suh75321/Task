package com.teamsparta.task.user.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;


    // 매개변수를 받는 생성자 추가
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}


