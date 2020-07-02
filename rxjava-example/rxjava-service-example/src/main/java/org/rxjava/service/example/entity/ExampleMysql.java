package org.rxjava.service.example.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table
public class ExampleMysql {
    @Id
    private Long id;
    private String name;
    @CreatedDate
    private LocalDateTime createDate;
}
