package com.todo.ToDo.controller;

import com.todo.ToDo.dto.AuthRequest;
import com.todo.ToDo.dto.RegisterRequest;
import com.todo.ToDo.security.JwtResponse;
import com.todo.ToDo.service.AuthService;
import com.todo.ToDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<JwtResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@RequestBody AuthRequest loginRequest){
        return ResponseEntity.ok(authService.login(loginRequest));
    }
}