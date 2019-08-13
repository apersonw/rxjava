package org.rxjava.service.manager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(
        @CompoundIndex(
                name = "managerId_roleId",
                def = "{'managerId': 1, 'roleId': 1}",
                unique = true
        )
)
@Data
public class ManagerRole {
    @Id
    private String id;
    private String managerId;
    private String roleId;
}
