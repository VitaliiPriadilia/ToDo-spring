package com.todo.ToDo.exceptions;

public class TaskException extends RuntimeException{
    public TaskException(String message, int value){
        super(message);
        this.httpStatusValue = value;
    }
    private final int httpStatusValue;
    public int getHttpStatusValue() {
        return httpStatusValue;
    }
}
