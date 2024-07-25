package com.teamsparta.task.user.controller;

import com.teamsparta.task.security.UserDetailsImpl;
import com.teamsparta.task.user.dto.LoginRequestDto;
import com.teamsparta.task.user.dto.LoginResponseDto;
import com.teamsparta.task.user.dto.SignupRequestDto;
import com.teamsparta.task.user.dto.UpdateUserRequestDto;
import com.teamsparta.task.user.model.User;
import com.teamsparta.task.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        String accessToken = userService.login(requestDto);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(
            @RequestBody UpdateUserRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser(); // 현재 로그인한 사용자 정보 가져오기

        // 요청 DTO의 정보를 사용하여 사용자 정보 업데이트
        userService.updateUser(requestDto, user);

        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        userService.deleteUser(user);
        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
    }

}
