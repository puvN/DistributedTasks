package com.puvn.distributedtasks.task.util;

import com.puvn.distributedtasks.task.dto.v1.Task;

public class MapUtil {

    public static com.puvn.distributedtasks.task.dto.v1.Task
    mapToDtoV1(com.puvn.distributedtasks.task.persist.Task entity) {
        if (entity == null) return new Task("", 0, null);
        return new Task(entity.getName(), entity.getDuration(), entity.getStatus());
    }

    public static com.puvn.distributedtasks.task.persist.Task
    mapEntity(String taskName, long durationMs) {
        return new com.puvn.distributedtasks.task.persist.Task(taskName, durationMs);
    }

    public static com.puvn.distributedtasks.task.compute.Task
    mapComputeTask(com.puvn.distributedtasks.task.persist.Task task) {
        return new com.puvn.distributedtasks.task.compute.Task(task.getName(), task.getDuration());
    }

}
