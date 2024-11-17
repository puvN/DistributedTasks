package com.puvn.distributedtasks.execution;

import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.task.compute.Task;
import com.puvn.distributedtasks.task.manager.TaskManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Slf4j
public class ExecutionServiceImpl implements ExecutionService {

    private final BlockingQueue<Task> taskQueue;

    public ExecutionServiceImpl(@Value("${workers.count:3}") int workerCount, TaskManager taskManager) {
        this.taskQueue = new LinkedBlockingQueue<>();
        log.info("====== Worker count: {}", workerCount);
        for (int i = 0; i < workerCount; i++) {
            var worker = new Worker("Worker-" + i, this.taskQueue, taskManager);
            Thread workerThread = new Thread(worker, worker.getName());
            log.debug("Starting worker thread: {}", workerThread.getName());
            workerThread.start();
        }
        log.info("======= All Workers initialized");
    }

    @Override
    public void putTask(String taskName, long durationMs) {
        Task task = new Task(taskName, durationMs);
        task.setStatus(TaskStatus.QUEUED);
        this.taskQueue.add(task);
    }
}
