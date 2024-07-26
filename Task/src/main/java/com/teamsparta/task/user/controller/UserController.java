package com.teamsparta.task.user.controller;

import com.teamsparta.task.user.dto.LoginRequestDto;
import com.teamsparta.task.user.dto.LoginResponseDto;
import com.teamsparta.task.user.dto.SignupRequestDto;
import com.teamsparta.task.user.service.UserService;
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
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto requestDto) {
        userService.signup(requestDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        String accessToken = userService.login(requestDto);
        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }
    //수정, 삭제는 요구사항에 없었다!!

//    @PutMapping("/update/{id}")
//    public ResponseEntity<String> updateUser(
//            @PathVariable Long id, // URL에서 사용자 ID를 입력받음
//            @RequestBody UpdateUserRequestDto requestDto,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        // userDetails가 null인지 확인
//        if (userDetails == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 인증되지 않았습니다.");
//        }
//
//        User currentUser = userDetails.getUser(); // 현재 로그인한 사용자 정보 가져오기
//
//        // 요청 DTO의 정보를 사용하여 사용자 정보를 업데이트
//        // 여기서 id가 currentUser의 id와 일치하는지 확인
//        if (!currentUser.getId().equals(id)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("자신의 정보만 수정할 수 있습니다.");
//        }
//
//        userService.updateUser(requestDto, currentUser);
//
//        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteUser(
//            @PathVariable Long id, // URL에서 사용자 ID를 입력받음
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ) {
//        // userDetails가 null인지 확인
//        if (userDetails == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 인증되지 않았습니다.");
//        }
//
//        User currentUser = userDetails.getUser();
//
//        // 요청된 ID와 현재 사용자의 ID가 일치하는지 확인
//        if (!currentUser.getId().equals(id)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("자신의 정보만 삭제할 수 있습니다.");
//        }
//
//        // 사용자 삭제 서비스 호출
//        userService.deleteUser(currentUser);
//        return ResponseEntity.ok("회원 탈퇴가 완료되었습니다.");
//    }


}
