package com.puvn.distributedtasks.controller;

import com.puvn.distributedtasks.dto.v1.Task;
import com.puvn.distributedtasks.exception.TaskNotFoundException;
import com.puvn.distributedtasks.task.manager.TaskManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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

    public TaskController(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Operation(summary = "Get a task by its name",
            description = "Retrieve details of a specific task by providing its name.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Task.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    @GetMapping("/{name}")
    public ResponseEntity<Task> getTask(
            @PathVariable @Valid @NotBlank @Parameter(ref = "#/components/parameters/taskName")
            String name) {
        var task = this.taskManager.getTask(name);
        if (task == null) {
            throw new TaskNotFoundException("Task with name '" + name + "' not found.");
        }
        return ResponseEntity.ok(task);
    }

}
