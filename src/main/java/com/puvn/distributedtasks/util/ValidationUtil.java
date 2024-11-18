package com.puvn.distributedtasks.util;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Getter
@Service
@Slf4j
public class ValidationUtil {

    private final Pattern TASK_NAME_PATTERN;

    private final String namePattern;

    public ValidationUtil(@Value("${tasks.name-pattern}") String namePattern) {
        log.info("Task name pattern is: {}", namePattern);
        this.namePattern = namePattern;
        TASK_NAME_PATTERN = Pattern.compile(namePattern);
    }

    public boolean isValidTaskName(String taskName) {
        if (taskName == null || taskName.isBlank()) {
            return false;
        }
        return TASK_NAME_PATTERN.matcher(taskName).matches();
    }

}
