package com.puvn.distributedtasks.controller;

import com.puvn.distributedtasks.dto.v1.ExecutionStatus;
import com.puvn.distributedtasks.execution.ExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class ExecutionController {

    private final ExecutionService executionService;

    public ExecutionController(ExecutionService executionService) {
        this.executionService = executionService;
    }

    @Operation(summary = "Get execution status", description = "Retrieve the status of the task execution service.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Execution status retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExecutionStatus.class)))
    })
    @GetMapping("/execution/status")
    public ResponseEntity<ExecutionStatus> getExecutionStatus() {
        return ResponseEntity.ok(this.executionService.getExecutionStatus());
    }

}
