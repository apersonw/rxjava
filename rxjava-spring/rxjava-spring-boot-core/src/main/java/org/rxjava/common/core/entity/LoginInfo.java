package org.rxjava.common.core.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author happy 2019-04-14 14:00
 * 登陆信息
 */
@Getter
@Setter
public class LoginInfo {
    /**
     * 用户Id
     */
    private ObjectId userId;
    /**
     * 用户登陆信息对应的token
     */
    private String token;
    /**
     * 应用Id
     */
    private ObjectId appId;
    /**
     * 过期时间
     */
    private LocalDateTime expireDate;
}