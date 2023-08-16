package com.todo.ToDo.controller;

import com.todo.ToDo.dto.TaskDto;
import com.todo.ToDo.model.Task;
import com.todo.ToDo.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task-list/")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("{task_list_id}")
    public ResponseEntity<?> getTasks(Principal principal, @PathVariable Long task_list_id){
        return ResponseEntity.ok(taskService.getTasks(principal.getName(), task_list_id));
    }

    @PostMapping("{task_list_id}")
    public ResponseEntity<?> postTask(Principal principal, @PathVariable Long task_list_id, @RequestBody TaskDto taskDto){
        this.taskService.postTask(principal.getName(), task_list_id, taskDto);
        return ResponseEntity.ok("Created new task");
    }

    @GetMapping("{task_list_id}/task/{task_id}")
    public ResponseEntity<?> getTask(String username, @PathVariable Long task_list_id, @PathVariable Long task_id){
        return ResponseEntity.ok(this.taskService.getTask(username, task_list_id, task_id));
    }

    @DeleteMapping("{task_list_id}/task/{task_id}")
    public ResponseEntity<?> deleteTask(Principal principal, @PathVariable Long task_list_id, @PathVariable Long task_id){
        this.taskService.deleteTask(principal.getName(), task_list_id, task_id);
        return ResponseEntity.ok("Delete task success");
    }

    @PatchMapping("{task_list_id}/task/{task_id}")
    public ResponseEntity<?> patchTask(Principal principal, @PathVariable Long task_list_id, @PathVariable Long task_id, @RequestBody TaskDto taskDto){
        this.taskService.patchTask(principal.getName(), task_list_id, task_id, taskDto);
        return ResponseEntity.ok("Patched success");
    }
}
