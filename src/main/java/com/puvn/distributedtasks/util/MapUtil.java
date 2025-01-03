package com.puvn.distributedtasks.util;

import com.puvn.distributedtasks.dto.v1.Task;
import com.puvn.distributedtasks.dto.v1.Worker;

public class MapUtil {

    public static Task
    mapToDtoV1(com.puvn.distributedtasks.task.persist.Task entity) {
        if (entity == null) return new Task("", 0, null, null, null);
        return new Task(entity.getName(), entity.getDuration(), entity.getStatus(),
                entity.getCreated(), entity.getUpdated());
    }

    public static com.puvn.distributedtasks.task.persist.Task
    mapEntity(String taskName, long durationMs) {
        return new com.puvn.distributedtasks.task.persist.Task(taskName, durationMs);
    }

    public static Worker mapToWorkerV1(com.puvn.distributedtasks.execution.Worker worker) {
        return new Worker(worker.getWorkerName(), worker.getWorkerStatus());
    }

}
