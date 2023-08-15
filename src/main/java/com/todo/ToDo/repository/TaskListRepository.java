package com.todo.ToDo.repository;

import com.todo.ToDo.model.TaskList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findAllByUserUsername(String username);
    Optional<TaskList> findTaskListByUserUsernameAndId(String username, Long id);
    Boolean existsTaskListByUserUsernameAndName(String username, String name);
}
