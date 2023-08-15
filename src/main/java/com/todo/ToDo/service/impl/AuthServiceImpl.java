package com.todo.ToDo.service.impl;

import com.todo.ToDo.dto.AuthRequest;
import com.todo.ToDo.dto.RegisterRequest;
import com.todo.ToDo.exceptions.UserException;
import com.todo.ToDo.model.User;
import com.todo.ToDo.security.JwtResponse;
import com.todo.ToDo.security.JwtService;
import com.todo.ToDo.security.PasswordEncoder;
import com.todo.ToDo.service.AuthService;
import com.todo.ToDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public JwtResponse login(AuthRequest authRequest) {
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authRequest.getUsername(), authRequest.getPassword()));
        }catch (BadCredentialsException badCredentialsException){
            throw new UserException("For user " + authRequest.getUsername() + " bad credentials", HttpStatus.BAD_REQUEST.value());
        }catch (InternalAuthenticationServiceException internalAuthenticationServiceException){
            throw new UserException("User with username " + authRequest.getUsername() + " not found", HttpStatus.NOT_FOUND.value());
        }
        return new JwtResponse(this.jwtService.generateJwtToken(this.userService.loadUserByUsername(authRequest.getUsername())));
    }

    @Override
    public JwtResponse register(RegisterRequest authRequest) {
        if(this.userService.existsUserByUsername(authRequest.getUsername())){
            throw new UserException("User with username " + authRequest.getUsername() + " already exist", HttpStatus.BAD_REQUEST.value());
        }
        if (this.userService.existsUserByEmail(authRequest.getEmail())) {
            throw new UserException("User with email " + authRequest.getEmail() + " already exist", HttpStatus.BAD_REQUEST.value());
        }
        return new JwtResponse(jwtService.generateJwtToken(this.userService.save(authRequest)));
    }
}
