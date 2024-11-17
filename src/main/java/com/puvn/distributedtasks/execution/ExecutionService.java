package com.puvn.distributedtasks.execution;

import com.puvn.distributedtasks.task.compute.Task;

public interface ExecutionService {

    void putTask(String taskName, long durationMs);

}
