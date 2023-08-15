package com.todo.ToDo.service;

import com.todo.ToDo.dto.AuthRequest;
import com.todo.ToDo.dto.RegisterRequest;
import com.todo.ToDo.model.User;
import com.todo.ToDo.security.JwtResponse;

public interface AuthService {
    JwtResponse login(AuthRequest authRequest);
    JwtResponse register(RegisterRequest authRequest);
}
