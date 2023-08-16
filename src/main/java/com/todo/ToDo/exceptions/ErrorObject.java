package com.todo.ToDo.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
@Data
public class ErrorObject {
    private String message;
    private int value;
    private LocalDate timestamp;
    private String entity;
}
