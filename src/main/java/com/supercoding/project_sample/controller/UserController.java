package com.supercoding.project_sample.controller;

import com.supercoding.project_sample.dto.LoginRequest;
import com.supercoding.project_sample.dto.LogoutRequest;
import com.supercoding.project_sample.dto.SignUpRequest;
import com.supercoding.project_sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody SignUpRequest signUpRequest){
        boolean isSuccess = userService.signUp(signUpRequest);
        if (isSuccess) {
            return ResponseEntity.ok(Map.of("message", "회원가입이 완료되었습니다."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "이미 등록된 이메일입니다."));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest loginRequest, HttpServletResponse httpServletResponse) throws Exception {
        String token = userService.login(loginRequest);
        Map<String, String> response = new HashMap<>();

        if (token != null) {
            httpServletResponse.setHeader("Authorization", "Bearer " + token);
            response.put("message", "로그인이 성공적으로 완료되었습니다.");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest) {
        boolean isLoggedOut = userService.logout(logoutRequest.getEmail());

        if (isLoggedOut) {
            return ResponseEntity.ok("로그아웃되었습니다.");
        } else {
            return ResponseEntity.badRequest().body("로그아웃 실패");
        }
    }


}
