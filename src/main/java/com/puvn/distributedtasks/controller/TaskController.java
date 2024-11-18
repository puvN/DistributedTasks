package com.puvn.distributedtasks.controller;

import com.puvn.distributedtasks.dto.v1.ExecutionStatus;
import com.puvn.distributedtasks.exception.TaskNotFoundException;
import com.puvn.distributedtasks.dto.v1.Task;
import com.puvn.distributedtasks.execution.ExecutionService;
import com.puvn.distributedtasks.task.manager.TaskManager;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tasks")
@Validated
public class TaskController {

    private final TaskManager taskManager;
    private final ExecutionService executionService;

    public TaskController(TaskManager taskManager, ExecutionService executionService) {
        this.taskManager = taskManager;
        this.executionService = executionService;
    }

    @GetMapping("/{name}")
    public ResponseEntity<Task> getTask(
            @PathVariable @Valid @NotBlank @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Invalid task name")
            String name) {
        var task = this.taskManager.getTask(name);
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' not found.");
        }
        return ResponseEntity.ok(task);
    }

    @GetMapping("/execution/status")
    public ResponseEntity<ExecutionStatus> getExecutionStatus() {
        return ResponseEntity.ok(this.executionService.getExecutionStatus());
    }


}
