package com.puvn.distributedtasks.execution;

import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.task.compute.Task;
import com.puvn.distributedtasks.task.manager.TaskManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class Worker extends Thread {
    @Getter
    private final String workerName;
    @Getter
    private WorkerStatus workerStatus;
    private final BlockingQueue<Task> taskQueue;
    private final TaskManager taskManager;

    public Worker(String name, BlockingQueue<Task> taskQueue, TaskManager taskManager) {
        this.workerName = name;
        this.taskQueue = taskQueue;
        this.taskManager = taskManager;
        workerStatus = WorkerStatus.INITIALIZED;
    }

    @Override
    public void run() {
        while (true) {
            workerStatus = WorkerStatus.AVAILABLE;
            try {
                Task task = taskQueue.take();
                workerStatus = WorkerStatus.TASK_ASSIGNED;
                updateTask(task, TaskStatus.EXECUTING);
                try {
                    log.info("Executing {} on Worker {}", task.getTaskName(), this.workerName);
                    workerStatus = WorkerStatus.BUSY;
                    task.run();
                    updateTask(task, TaskStatus.COMPLETED);
                    log.info("Completed {} on Worker {}", task.getTaskName(), this.workerName);
                } catch (Exception e) {
                    log.error("Error with task {}, {}", task.getTaskName(), e.getMessage());
                    updateTask(task, TaskStatus.ERROR);
                } finally {
                    workerStatus = WorkerStatus.AVAILABLE;
                }
            } catch (InterruptedException e) {
                log.error("Fatal error, could not take message from queue, interrupting {}", this.workerName, e);
                Thread.currentThread().interrupt();
                workerStatus = WorkerStatus.FIRING;
                break;
            }
        }
    }

    private void updateTask(Task task, TaskStatus status) {
        task.setStatus(status);
        taskManager.updateTaskStatus(task.getTaskName(), status);
    }

}
