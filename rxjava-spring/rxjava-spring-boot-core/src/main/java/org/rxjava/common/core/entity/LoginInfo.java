package org.rxjava.common.core.entity;

import lombok.Getter;
import lombok.Setter;

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
    private String userId;
    /**
     * 用户登陆信息对应的token
     */
    private String token;
    /**
     * 合作伙伴Id
     */
    private String partnerId;
    /**
     * 过期时间
     */
    private LocalDateTime expireDate;
}