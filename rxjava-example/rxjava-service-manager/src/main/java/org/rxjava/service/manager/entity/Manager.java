package org.rxjava.service.manager.entity;

import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Manager {
    @Id
    private String id;
    private String name;
    @Indexed(unique = true)
    private String phone;
}
