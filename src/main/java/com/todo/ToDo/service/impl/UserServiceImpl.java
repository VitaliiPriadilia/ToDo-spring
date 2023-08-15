package com.todo.ToDo.service.impl;

import com.todo.ToDo.dto.RegisterRequest;
import com.todo.ToDo.exceptions.UserException;
import com.todo.ToDo.model.Role;
import com.todo.ToDo.model.User;
import com.todo.ToDo.repository.UserRepository;
import com.todo.ToDo.security.PasswordEncoder;
import com.todo.ToDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UserException("User with username "+username+" not found", HttpStatus.NOT_FOUND.value()));
    }
    @Override
    public Boolean existsUserByUsername(String username){
        return this.userRepository.existsUserByUsername(username);
    }
    @Override
    public Boolean existsUserByEmail(String email){
        return this.userRepository.existsUserByEmail(email);
    }
    @Override
    public User save(RegisterRequest registerRequest) {
        return this.userRepository.save(User.builder()
                        .email(registerRequest.getEmail())
                        .password(passwordEncoder.customerPasswordEncoder().encode(registerRequest.getPassword()))
                        .role(Role.USER)
                        .username(registerRequest.getUsername())
                        .build());
    }
}
