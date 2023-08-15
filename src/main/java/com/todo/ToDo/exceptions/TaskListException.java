package com.todo.ToDo.exceptions;

public class TaskListException extends RuntimeException{
    public TaskListException(String message, int value){
        super(message);
    }
}
