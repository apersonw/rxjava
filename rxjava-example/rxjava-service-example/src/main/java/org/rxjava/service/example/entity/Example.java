package org.rxjava.service.example.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document
public class Example {
    @Id
    private ObjectId id;
    private String name;
    @CreatedDate
    private LocalDateTime createDate;
}
