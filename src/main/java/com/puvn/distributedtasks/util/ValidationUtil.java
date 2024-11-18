package com.puvn.distributedtasks.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Getter
@Component
public class ValidationUtil {

    private final Pattern TASK_NAME_PATTERN;

    @Value("${tasks.name-pattern}")
    private String namePattern;

    public ValidationUtil() {
        TASK_NAME_PATTERN = Pattern.compile(namePattern);
    }

    public boolean isValidTaskName(String taskName) {
        if (taskName == null || taskName.isBlank()) {
            return false;
        }
        return TASK_NAME_PATTERN.matcher(taskName).matches();
    }

}
