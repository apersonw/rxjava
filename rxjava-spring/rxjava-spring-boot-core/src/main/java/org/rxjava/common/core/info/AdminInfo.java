package org.rxjava.common.core.info;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * 管理信息
 */
@Data
public class AdminInfo implements LoginInfo{
    /**
     * 管理员Id
     */
    private ObjectId managerId;
}
