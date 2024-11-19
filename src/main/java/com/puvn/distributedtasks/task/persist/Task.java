package com.puvn.distributedtasks.task.persist;

import com.puvn.distributedtasks.task.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Entity
@Table(name = "task")
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_seq_gen")
    @SequenceGenerator(name = "task_id_seq_gen", sequenceName = "task_id_seq", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private long duration;

    @Column
    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @CreatedDate
    private LocalDateTime created;

    @LastModifiedDate
    private LocalDateTime updated;

    public Task(String name, long duration) {
        this.name = name;
        this.duration = duration;
    }

}
