package com.todo.ToDo.exceptions;

import com.todo.ToDo.model.TaskList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class HandlerException {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<?> userException(UserException exception){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setTimestamp(LocalDate.now());
        errorObject.setEntity("User");
        errorObject.setMessage(exception.getMessage());
        errorObject.setValue(exception.getHttpStatusValue());
        return new ResponseEntity<>(errorObject, HttpStatus.valueOf(exception.getHttpStatusValue()));
    }

    @ExceptionHandler(TaskException.class)
    public ResponseEntity<?> taskException(TaskException exception){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setTimestamp(LocalDate.now());
        errorObject.setEntity("Task");
        errorObject.setMessage(exception.getMessage());
        errorObject.setValue(exception.getHttpStatusValue());
        return new ResponseEntity<>(errorObject, HttpStatus.valueOf(exception.getHttpStatusValue()));
    }

    @ExceptionHandler(TaskListException.class)
    public ResponseEntity<?> taskListException(TaskException exception){
        ErrorObject errorObject = new ErrorObject();
        errorObject.setTimestamp(LocalDate.now());
        errorObject.setEntity("TaskList");
        errorObject.setMessage(exception.getMessage());
        errorObject.setValue(exception.getHttpStatusValue());
        return new ResponseEntity<>(errorObject, HttpStatus.valueOf(exception.getHttpStatusValue()));
    }
}
