package com.todo.ToDo.controller;

import com.todo.ToDo.dto.TaskListDto;
import com.todo.ToDo.service.TaskListService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task-list/")
public class TaskListController {
    private final TaskListService taskListService;

    @GetMapping
    public ResponseEntity<?> getTaskLists(Principal principal){
        return ResponseEntity.ok(this.taskListService.getTaskLists(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<?> postTaskList(Principal principal, @RequestBody TaskListDto taskListDto){
        this.taskListService.postTaskList(principal.getName(), taskListDto);
        return ResponseEntity.ok("Posted new task list");
    }

    @DeleteMapping("{task_id}")
    public ResponseEntity<?> deleteTaskList(Principal principal, @PathVariable Long task_id){
        this.taskListService.deleteTaskList(principal.getName(), task_id);
        return ResponseEntity.ok("Deleted task list");
    }

    @PatchMapping("{task_id}")
    public ResponseEntity<?> patchTaskList(Principal principal, @PathVariable Long task_id, @RequestBody TaskListDto taskListDto){
        this.taskListService.patchTaskList(principal.getName(), task_id, taskListDto);
        return ResponseEntity.ok("Patched success");
    }
}
