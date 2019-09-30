package org.rxjava.service.example.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

/**
 * 组分类
 */
@Data
@Document
public class GroupCategory {
    @Id
    private String id;
    private String name;
    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;
}
