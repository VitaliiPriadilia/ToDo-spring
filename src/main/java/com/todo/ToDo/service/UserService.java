package com.todo.ToDo.service;

import com.todo.ToDo.dto.RegisterRequest;
import com.todo.ToDo.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public Boolean existsUserByUsername(String username);
    public Boolean existsUserByEmail(String email);
    public User save(RegisterRequest registerRequest);

}
