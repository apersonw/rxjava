package org.rxjava.service.manager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(
        @CompoundIndex(
                name = "path_method",
                def = "{'path': 1, 'method': 1}",
                unique = true
        )
)
@Data
public class Permission {
    @Id
    private String id;
    private String name;
    private String path;
    private String method;
}
