package com.puvn.distributedtasks.task.manager;

import com.puvn.distributedtasks.exception.TaskAlreadyExistsException;
import com.puvn.distributedtasks.exception.TaskNotFoundException;
import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.dto.v1.Task;
import com.puvn.distributedtasks.task.persist.TaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.puvn.distributedtasks.util.MapUtil.mapEntity;
import static com.puvn.distributedtasks.util.MapUtil.mapToDtoV1;

@Service
@Slf4j
@Transactional(readOnly = true)
public class TaskManagerImpl implements TaskManager {

    private final TaskRepository taskRepository;

    public TaskManagerImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Transactional
    public Task registerTask(String taskName, long durationMs) {
        if (this.taskRepository.findByName(taskName).isPresent()) {
            throw new TaskAlreadyExistsException("Task with name " + taskName + " already exists");
        }
        log.info("Registering new task {}", taskName);
        var entity = mapEntity(taskName, durationMs);
        entity.setStatus(TaskStatus.REGISTERED);
        entity = this.taskRepository.save(entity);
        return mapToDtoV1(entity);
    }

    @Override
    @Transactional
    public void updateTaskStatus(String taskName, TaskStatus status) {
        var optionalEntity = this.taskRepository.findByName(taskName);
        if (optionalEntity.isPresent()) {
            var entity = optionalEntity.get();
            entity.setStatus(status);
            this.taskRepository.save(entity);
        } else {
            throw new TaskNotFoundException("Task with name " + taskName + " does not exist");
        }
    }

    @Override
    public Task getTask(String taskName) {
        var optionalEntity = this.taskRepository.findByName(taskName);
        if (optionalEntity.isPresent()) {
            var task = optionalEntity.get();
            return mapToDtoV1(task);
        } else {
            throw new TaskNotFoundException("Task with name " + taskName + " does not exist");
        }
    }

}
