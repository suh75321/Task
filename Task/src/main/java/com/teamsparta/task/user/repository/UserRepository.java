package com.teamsparta.task.user.repository;

import com.teamsparta.task.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id); // ID로 사용자 찾기

//    Optional<User> findByEmail(String email); 이메일 필요없음
}