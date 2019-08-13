package org.rxjava.service.manager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(
        @CompoundIndex(
                name = "managerId_roleId_permissionId",
                def = "{'managerId': 1,'roleId': 1, 'permissionId': 1}",
                unique = true
        )
)
@Data
public class RolePermission {
    @Id
    private String id;
    private String roleId;
    private String permissionId;
    /**
     * 冗余管理员Id，便于通过管理员Id查询权限
     */
    private String managerId;
}
