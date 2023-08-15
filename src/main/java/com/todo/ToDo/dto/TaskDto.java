package com.todo.ToDo.dto;

import com.todo.ToDo.model.TaskList;
import com.todo.ToDo.model.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate deadline;
    private TaskStatus status;
}
