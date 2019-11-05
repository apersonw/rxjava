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
 * 商品
 */
@Data
@Document
public class Goods {
    /**
     * 商品Id
     */
    @Id
    private String id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 封面图
     */
    private String coverImgUrl;
    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateDate;
}
