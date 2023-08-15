package com.todo.ToDo.service.impl;

import com.todo.ToDo.dto.TaskListDto;
import com.todo.ToDo.exceptions.TaskListException;
import com.todo.ToDo.model.TaskList;
import com.todo.ToDo.model.User;
import com.todo.ToDo.repository.TaskListRepository;
import com.todo.ToDo.service.TaskListService;
import com.todo.ToDo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskListServiceImpl implements TaskListService {
    private final TaskListRepository taskListRepository;
    private final UserService userService;
    @Override
    public List<TaskListDto> getTaskLists(String username) {
        List<TaskList> lists = this.taskListRepository.findAllByUserUsername(username);
        if(lists.isEmpty()){
            throw new TaskListException("Task lists for user "+username+" not found", HttpStatus.NOT_FOUND.value());
        }
        return lists.stream().map(TaskListServiceImpl::mapToDto).toList();
    }

    @Override
    public TaskListDto getTaskList(String username, Long task_id) {
        Optional<TaskList> taskList = this.taskListRepository.findTaskListByUserUsernameAndId(username, task_id);
        if(taskList.isEmpty()){
            throw new TaskListException("Task list with id "+task_id+" for user "+username+" not found", HttpStatus.NOT_FOUND.value());
        }
        return mapToDto(taskList.get());
    }

    @Override
    public void postTaskList(String username, TaskListDto taskListDto) {
        if(this.taskListRepository.existsTaskListByUserUsernameAndName(username, taskListDto.getName())){
            throw new TaskListException("Task list with name "+taskListDto.getName()+" for user "+username+" already exist", HttpStatus.BAD_REQUEST.value());
        }
        this.taskListRepository.save(TaskList.builder()
                        .user((User) userService.loadUserByUsername(username))
                        .name(taskListDto.getName())
                        .build());
    }

    @Override
    public void deleteTaskList(String username, Long task_id) {
        Optional<TaskList> taskList = this.taskListRepository.findTaskListByUserUsernameAndId(username, task_id);
        if(taskList.isEmpty()){
            throw new TaskListException("Task list with id "+task_id+" for user "+username+" not found", HttpStatus.NOT_FOUND.value());
        }
        this.taskListRepository.delete(taskList.get());
    }

    @Override
    public void patchTaskList(String username, Long task_id, TaskListDto taskListDto) {
        Optional<TaskList> taskList = this.taskListRepository.findTaskListByUserUsernameAndId(username, task_id);
        if(taskList.isEmpty()){
            throw new TaskListException("Task list with id "+task_id+" for user "+username+" not found", HttpStatus.NOT_FOUND.value());
        }
        taskList.get().setName(taskListDto.getName());
        this.taskListRepository.save(taskList.get());
    }

    public static TaskListDto mapToDto(TaskList taskList){
        return TaskListDto.builder()
                .id(taskList.getId())
                .name(taskList.getName())
                .build();
    }
}
