package com.todo.ToDo.service;

import com.todo.ToDo.dto.TaskDto;
import com.todo.ToDo.dto.TaskResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

public interface TaskService {
    TaskResponse getTask(String username, Long task_list_id, Long task_id);
    TaskResponse getTasks(String username, Long task_list_id);
    void postTask(String username, Long task_list_id, TaskDto taskDto);
    void patchTask(String username, Long task_list_id, Long task_id, TaskDto taskDto);
    void deleteTask(String username, Long task_list_id, Long task_id);
}
