package com.todo.ToDo.service.impl;

import com.todo.ToDo.dto.TaskDto;
import com.todo.ToDo.dto.TaskListDto;
import com.todo.ToDo.exceptions.TaskException;
import com.todo.ToDo.model.Task;
import com.todo.ToDo.repository.TaskListRepository;
import com.todo.ToDo.repository.TaskRepository;
import com.todo.ToDo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;
    @Override
    public TaskDto getTask(String username, Long task_list_id, Long task_id) {
        Optional<Task> task = this.taskRepository.findTaskByTaskListUserUsernameAndTaskListIdAndId(username, task_list_id, task_id);
        if(task.isEmpty()){
            throw  new TaskException("Task " + task_id + " in task list " + task_list_id + " user " + username +" not found", HttpStatus.NO_CONTENT.value());
        }
        return mapToDto(task.get());
    }

    @Override
    public List<TaskDto> getTasks(String username, Long task_list_id) {
        List<Task> list = this.taskRepository.findAllByTaskListIdAndTaskListUserUsername(task_list_id, username);
        if(list.isEmpty()){
            throw new TaskException("List is empty", HttpStatus.NO_CONTENT.value());
        }
        return list.stream().map(TaskServiceImpl::mapToDto).toList();
    }

    @Override
    public void postTask(String username, Long task_list_id, TaskDto taskDto) {
        if(this.taskRepository.existsByTaskListIdAndTaskListUserUsernameAndTitle(task_list_id, username, taskDto.getTitle())){
            throw new TaskException("Exist with this name", HttpStatus.BAD_REQUEST.value());
        }
        this.taskRepository.save(Task.builder()
                        .taskList(taskListRepository.findById(task_list_id).get())
                        .deadline(taskDto.getDeadline())
                        .description(taskDto.getDescription())
                        .status(taskDto.getStatus())
                        .title(taskDto.getTitle())
                        .build());
    }

    @Override
    public void patchTask(String username, Long task_list_id, Long task_id, TaskDto taskDto) {
        if(this.taskRepository.existsByTaskListIdAndTaskListUserUsernameAndTitle(task_list_id, username, taskDto.getTitle())){
            throw new TaskException("Exist with this name", HttpStatus.BAD_REQUEST.value());
        }

        Optional<Task> task = this.taskRepository.findTaskByTaskListUserUsernameAndTaskListIdAndId(username,task_list_id, task_id);
        if(task.isEmpty()){
            throw new TaskException("Not found to change", HttpStatus.NOT_FOUND.value());
        }
        task.get().setDescription(taskDto.getDescription());
        task.get().setTitle(taskDto.getTitle());
        task.get().setStatus(taskDto.getStatus());
        task.get().setDeadline(taskDto.getDeadline());
        this.taskRepository.save(task.get());
    }

    @Override
    public void deleteTask(String username, Long task_list_id, Long task_id) {
        Optional<Task> task = this.taskRepository.findTaskByTaskListUserUsernameAndTaskListIdAndId(username,task_list_id, task_id);
        if(task.isEmpty()){
            throw new TaskException("Not found to change", HttpStatus.NOT_FOUND.value());
        }
        this.taskRepository.delete(task.get());
    }

    public static TaskDto mapToDto(Task task){
        return TaskDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .deadline(task.getDeadline())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }
}
