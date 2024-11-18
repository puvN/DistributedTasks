package com.puvn.distributedtasks.dto.v1;

import java.util.List;

public record ExecutionStatus(int queueTaskSize, List<Worker> workers
) {}
