package org.rxjava.service.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 分组
 */
@Data
@Document
public class Group {
    @Id
    private String id;
    /**
     * 组分类Id
     */
    @Indexed
    private String groupCategoryId;
    private String name;
}
