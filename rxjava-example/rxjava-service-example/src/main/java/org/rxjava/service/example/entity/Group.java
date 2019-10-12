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
    /**
     * 分组名称，如某某主题，某某秒杀
     */
    private String name;
    /**
     * 组排序号
     */
    private int sort;
    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;
}
