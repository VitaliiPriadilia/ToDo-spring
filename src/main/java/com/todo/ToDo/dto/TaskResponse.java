package com.todo.ToDo.dto;

import com.todo.ToDo.model.TaskList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private TaskListDto taskList;
    private List<TaskDto> tasks;
    private LocalDate timestamp;
}
