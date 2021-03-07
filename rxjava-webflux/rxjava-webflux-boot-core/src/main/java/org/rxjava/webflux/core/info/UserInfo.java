package org.rxjava.webflux.core.info;

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
     * 当前登录账号Id
     */
    private ObjectId accountId;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 应用Id(此字段不可为null)，优先从token获取，若无token由前端传
     */
    private ObjectId appId;
}