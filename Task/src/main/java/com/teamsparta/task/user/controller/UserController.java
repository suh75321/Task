package com.teamsparta.task.user.controller;

import com.teamsparta.task.user.dto.LoginRequestDto;
import com.teamsparta.task.user.dto.LoginResponseDto;
import com.teamsparta.task.user.dto.SignupRequestDto;
import com.teamsparta.task.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        String accessToken = userService.login(requestDto);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
}
