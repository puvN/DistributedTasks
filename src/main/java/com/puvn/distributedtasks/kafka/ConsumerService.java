package com.puvn.distributedtasks.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.puvn.distributedtasks.execution.ExecutionService;
import com.puvn.distributedtasks.task.TaskStatus;
import com.puvn.distributedtasks.dto.v1.Task;
import com.puvn.distributedtasks.task.manager.TaskManager;
import com.puvn.distributedtasks.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ConsumerService {

    private final TaskManager taskManager;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ExecutionService executionService;

    private final ValidationUtil validationUtil;

    @Value("${consumer.dead-letter-topic:dead_letter_topic}")
    private String deadLetterTopic;

    public ConsumerService(TaskManager taskManager, KafkaTemplate<String, String> kafkaTemplate,
                           ExecutionService executionService, ValidationUtil validationUtil) {
        this.taskManager = taskManager;
        this.kafkaTemplate = kafkaTemplate;
        this.executionService = executionService;
        this.validationUtil = validationUtil;
    }

    @KafkaListener(topics = "tasks-topic", groupId = "task-executor-group")
    public void consumeTask(ConsumerRecord<String, String> record) {
        try {
            log.info("Received record: {}", record);
            Task task = parseTask(record.value());
            task = taskManager.registerTask(task.name(), task.durationMs());
            if (task != null && task.status() == TaskStatus.REGISTERED) {
                this.executionService.putTask(task.name(), task.durationMs());
            }
        } catch (IllegalArgumentException parseException) {
            log.error("Error parsing task: {}", parseException.getMessage());
            sendToDeadLetterTopic(record.value(), "PARSE_ERROR");
        } catch (Exception taskManagerException) {
            log.error("Error in TaskManager: {}", taskManagerException.getMessage());
            sendToDeadLetterTopic(record.value(), "TASK_MANAGER_ERROR");
        }
    }

    private void sendToDeadLetterTopic(String originalPayload, String errorType) {
        String deadLetterMessage =
                String.format("{\"errorType\": \"%s\", \"payload\": %s}", errorType, originalPayload);
        kafkaTemplate.send(deadLetterTopic, deadLetterMessage);
    }

    private Task parseTask(String taskJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Task task = objectMapper.readValue(taskJson, Task.class);
            if (!validationUtil.isValidTaskName(task.name())) {
                throw new Exception();
            }
            return task;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid task name or task format", e);
        }
    }

}
