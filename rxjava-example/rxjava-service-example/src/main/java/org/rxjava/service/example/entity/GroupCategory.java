package org.rxjava.service.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 组分类
 */
@Data
@Document
public class GroupCategory {
    @Id
    private String id;
    private String name;
}
