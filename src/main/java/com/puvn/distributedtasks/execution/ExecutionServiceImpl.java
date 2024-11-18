package com.puvn.distributedtasks.execution;

import com.puvn.distributedtasks.dto.v1.ExecutionStatus;
import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.task.compute.Task;
import com.puvn.distributedtasks.task.manager.TaskManager;
import com.puvn.distributedtasks.task.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Сервис выполнения задач, каждый инстанс сервиса имеет очередь и список воркеров. Для отображения
 * состояния сервиса (списка запущенных воркеров, их статусов, состояние очереди - потребуются понодные запросы,
 * что можно было бы сделать и более централизованно, но не в рамках данного задания.
 */
@Service
@Slf4j
public class ExecutionServiceImpl implements ExecutionService {

    private final BlockingQueue<Task> taskQueue;

    private final List<Worker> workers;

    public ExecutionServiceImpl(@Value("${workers.count:3}") int workerCount, TaskManager taskManager) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new ArrayList<>(workerCount);
        log.info("====== Worker count: {}", workerCount);
        for (int i = 0; i < workerCount; i++) {
            var worker = new Worker("TaskWorker-" + i, this.taskQueue, taskManager);
            workers.add(worker);
            log.info("Starting worker: {}", worker.getName());
            worker.start();
        }
        log.info("======= All Workers have been initialized");
    }

    @Override
    public void putTask(String taskName, long durationMs) {
        Task task = new Task(taskName, durationMs);
        task.setStatus(TaskStatus.QUEUED);
        this.taskQueue.add(task);
    }

    @Override
    public ExecutionStatus getExecutionStatus() {
        return new ExecutionStatus(taskQueue.size(),
                workers.stream().map(MapUtil::mapToWorkerV1).toList());
    }
}
