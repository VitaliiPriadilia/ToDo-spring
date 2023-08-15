package com.todo.ToDo.repository;

import com.todo.ToDo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findTaskByTaskListUserUsernameAndTaskListIdAndId(String username, Long task_list_id, Long id);
    List<Task> findAllByTaskListIdAndTaskListUserUsername(Long task_list_id, String username);
    Boolean existsByTaskListIdAndTaskListUserUsernameAndTitle(Long task_list_id, String username, String title);
}
