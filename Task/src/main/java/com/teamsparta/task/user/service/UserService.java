package com.teamsparta.task.user.service;

import com.teamsparta.task.jwt.JwtUtil;
import com.teamsparta.task.user.dto.LoginRequestDto;
import com.teamsparta.task.user.dto.SignupRequestDto;
import com.teamsparta.task.user.model.User;
import com.teamsparta.task.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.teamsparta.task.user.model.UserRoleEnum; // 추가
import java.time.LocalDateTime;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil; // 로그인을 위해 추가

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil; // 여기도
    }

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        String nickname = requestDto.getNickname();
        UserRoleEnum role = UserRoleEnum.USER;

        // 회원 중복 확인
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("중복된 username 입니다."); // 중복된 username 처리
        }

        // 사용자 등록
        User user = new User(nickname, username, password, role, LocalDateTime.now());
        userRepository.save(user);
    }

    public String login(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다.")); // 정보 불일치 처리

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("회원을 찾을 수 없습니다."); // 비밀번호 불일치 처리
        }

        // JWT 토큰 생성
        return jwtUtil.createToken(user.getUsername());
    }
//수정, 삭제는 요구사항 없었다
//    public void updateUser(UpdateUserRequestDto requestDto, User user) {
//        user.setUsername(requestDto.getUsername());
//        user.setNickname(requestDto.getNickname());
//        userRepository.save(user);
//    }
//
//    public void deleteUser(User user) {
//        userRepository.delete(user);
//    }
}
