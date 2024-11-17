package com.puvn.distributedtasks.task.dto.v1;

import com.puvn.distributedtasks.task.TaskStatus;

public record Task(String name, long durationMs, TaskStatus status) {
}
