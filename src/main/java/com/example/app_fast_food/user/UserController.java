package com.example.app_fast_food.user;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app_fast_food.user.dto.AuthDto;
import com.example.app_fast_food.user.dto.UserListResponseDto;
import com.example.app_fast_food.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMe(@AuthenticationPrincipal AuthDto auth) {
        return ResponseEntity.ok(userService.getMe(auth));
    }

    @GetMapping
    public ResponseEntity<List<UserListResponseDto>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }
}
