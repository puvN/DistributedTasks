package com.puvn.distributedtasks.execution;

import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.task.compute.Task;
import com.puvn.distributedtasks.task.manager.TaskManager;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;

@Slf4j
public class Worker implements Runnable {
    @Getter
    private final String name;
    private final BlockingQueue<Task> taskQueue;
    private final TaskManager taskManager;

    public Worker(String name, BlockingQueue<Task> taskQueue, TaskManager taskManager) {
        this.name = name;
        this.taskQueue = taskQueue;
        this.taskManager = taskManager;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskQueue.take();
                updateTask(task, TaskStatus.EXECUTING);
                try {
                    task.run();
                    updateTask(task, TaskStatus.COMPLETED);
                } catch (Exception e) {
                    log.error("Error with task {}, {}", task.getTaskName(), e.getMessage());
                    updateTask(task, TaskStatus.ERROR);
                }
            } catch (InterruptedException e) {
                log.error("Fatal error, could not take message from queue, interrupting {}", this.name, e);
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void updateTask(Task task, TaskStatus status) {
        task.setStatus(status);
        taskManager.updateTaskStatus(task.getTaskName(), status);
    }

}
