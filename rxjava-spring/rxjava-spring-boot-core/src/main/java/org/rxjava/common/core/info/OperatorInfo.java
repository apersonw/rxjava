package org.rxjava.common.core.info;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * 操作员信息
 */
@Data
public class OperatorInfo implements LoginInfo {
    /**
     * 操作员Id
     */
    private ObjectId operatorId;
    /**
     * 操作员角色Id
     */
    private ObjectId roleId;
}
