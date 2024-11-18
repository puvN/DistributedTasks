package com.puvn.distributedtasks.task.manager;

import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.dto.v1.Task;

public interface TaskManager {

    Task registerTask(String taskName, long durationMs);

    void updateTaskStatus(String taskName, TaskStatus status);

    Task getTask(String taskName);

}
