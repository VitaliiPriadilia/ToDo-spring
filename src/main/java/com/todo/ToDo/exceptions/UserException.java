package com.todo.ToDo.exceptions;

public class UserException extends RuntimeException{
    private final int httpStatusValue;
    public UserException(String message, int value){
        super(message);
        this.httpStatusValue = value;
    }
    public int getHttpStatusValue() {
        return httpStatusValue;
    }

}
