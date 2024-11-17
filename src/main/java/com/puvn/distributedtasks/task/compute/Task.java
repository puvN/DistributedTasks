package com.puvn.distributedtasks.task.compute;

import com.puvn.distributedtasks.task.TaskStatus;
import lombok.Data;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Task {

    private final String taskName;
    private final long durationMs;
    @Setter
    private TaskStatus status;

    public Task(String taskName, long durationMs) {
        this.taskName = taskName;
        this.durationMs = durationMs;
    }

    @SneakyThrows
    public void run() {
        // Simulation only, no actual logic executed
        log.info("Starting task {}", taskName);
        Thread.sleep(durationMs);
        log.info("Completed task {}", taskName);
    }

}
