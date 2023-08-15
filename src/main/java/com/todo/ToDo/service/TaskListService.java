package com.todo.ToDo.service;

import com.todo.ToDo.dto.TaskListDto;
import java.util.List;

public interface TaskListService {
    public List<TaskListDto> getTaskLists(String username);
    public TaskListDto getTaskList(String username, Long task_id);
    public void postTaskList(String username, TaskListDto taskListDto);
    public void deleteTaskList(String username, Long task_id);
    public void patchTaskList(String username, Long task_id, TaskListDto taskListDto);
}
