package com.todo.ToDo.exceptions;

public class UserException extends RuntimeException{
    public UserException(String message, int value){
        super(message);
    }
}
