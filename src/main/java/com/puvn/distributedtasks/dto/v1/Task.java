package com.puvn.distributedtasks.dto.v1;

import com.puvn.distributedtasks.task.TaskStatus;

public record Task(String name, long durationMs, TaskStatus status) {
}
