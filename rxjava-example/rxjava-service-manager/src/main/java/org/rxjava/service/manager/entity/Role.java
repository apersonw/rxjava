package org.rxjava.service.manager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Role {
    @Id
    private String id;
    private String name;
}
