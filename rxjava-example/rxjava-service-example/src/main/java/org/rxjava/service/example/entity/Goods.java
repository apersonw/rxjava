package org.rxjava.service.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
