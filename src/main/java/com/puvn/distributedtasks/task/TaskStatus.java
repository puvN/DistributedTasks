package com.puvn.distributedtasks.task;

public enum TaskStatus {
    REGISTERED, // persisted in the system
    QUEUED, // enqueued for execution on workers
    EXECUTING, // started execution on worker
    COMPLETED, // worker completed task
    ERROR
}
