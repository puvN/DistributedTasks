package com.puvn.distributedtasks.config;

import com.puvn.distributedtasks.util.ValidationUtil;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenApiCustomizer customTaskNameSchema(ValidationUtil validationUtil) {
        return openApi -> {
            @SuppressWarnings("rawtypes") Schema stringSchema = new StringSchema()
                    .pattern(validationUtil.getNamePattern())
                    .description("The name of the task")
                    .example("task_2020-01-01");

            Parameter taskNameParameter = new Parameter()
                    .name("name")
                    .in("path")
                    .required(true)
                    .schema(stringSchema)
                    .description("Task name must match the pattern: " + validationUtil.getNamePattern());

            openApi.getComponents().addParameters("name", taskNameParameter);
        };
    }
}
