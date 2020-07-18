package org.rxjava.common.core.info;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * 管理员信息
 */
@Data
public class ManagerInfo implements LoginInfo {
    /**
     * 管理员Id
     */
    private ObjectId managerId;
    /**
     * 管理员角色Id
     */
    private ObjectId roleId;
}
