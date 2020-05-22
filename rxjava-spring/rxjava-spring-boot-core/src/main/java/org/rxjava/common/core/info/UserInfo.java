package org.rxjava.common.core.info;

import lombok.Data;
import org.bson.types.ObjectId;

/**
 * @author happy 2019-04-14 14:00
 * 登陆信息
 */
@Data
public class UserInfo implements LoginInfo {
    /**
     * 用户Id
     */
    private ObjectId userId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 应用Id
     */
    private ObjectId appId;
}