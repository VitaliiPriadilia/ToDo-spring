package com.todo.ToDo.service;

import com.todo.ToDo.dto.TaskDto;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.List;

public interface TaskService {
    TaskDto getTask(String username, Long task_list_id, Long task_id);
    List<TaskDto> getTasks(String username, Long task_list_id);
    void postTask(String username, Long task_list_id, TaskDto taskDto);
    void patchTask(String username, Long task_list_id, Long task_id, TaskDto taskDto);
    void deleteTask(String username, Long task_list_id, Long task_id);
}
