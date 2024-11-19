package com.puvn.distributedtasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DistributedTasksApplication {

    public static void main(String[] args) {
        SpringApplication.run(DistributedTasksApplication.class, args);
    }

}
