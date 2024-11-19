package com.puvn.distributedtasks.dto.v1;

import com.puvn.distributedtasks.task.TaskStatus;

import java.time.LocalDateTime;

public record Task(String name, long durationMs, TaskStatus status, LocalDateTime created, LocalDateTime updated) {
}
