package com.puvn.distributedtasks.execution;

import com.puvn.distributedtasks.dto.v1.ExecutionStatus;

public interface ExecutionService {

    void putTask(String taskName, long durationMs);

    ExecutionStatus getExecutionStatus();

}
