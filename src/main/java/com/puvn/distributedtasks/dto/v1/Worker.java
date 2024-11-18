package com.puvn.distributedtasks.dto.v1;

import com.puvn.distributedtasks.execution.WorkerStatus;

public record Worker(String name, WorkerStatus status) {
}
