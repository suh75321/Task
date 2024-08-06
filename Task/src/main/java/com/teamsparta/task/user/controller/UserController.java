package com.teamsparta.task.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teamsparta.task.jwt.JwtUtil;
import com.teamsparta.task.kakao.KakaoService;
import com.teamsparta.task.user.dto.LoginRequestDto;
import com.teamsparta.task.user.dto.LoginResponseDto;
import com.teamsparta.task.user.dto.SignupRequestDto;
import com.teamsparta.task.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final KakaoService kakaoService;


    public UserController(UserService userService, KakaoService kakaoService) {
        this.userService = userService;
        this.kakaoService = kakaoService;
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

    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        // code: 카카오 서버로부터 받은 인가 코드 Service 전달 후 인증 처리 및 JWT 반환
        String token = kakaoService.kakaoLogin(code);

        // Cookie 생성 및 직접 브라우저에 Set
        Cookie cookie = new Cookie(JwtUtil.AUTHORIZATION_HEADER, token.substring(7));
        cookie.setPath("/");
        response.addCookie(cookie);

        return "redirect:/";
    }
}
