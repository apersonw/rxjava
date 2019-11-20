package org.rxjava.common.core.entity;

import lombok.Data;

/**
 * @author happy 2019-04-14 14:00
 * 登陆信息
 */
@Data
public class LoginInfo {
    /**
     * 用户Id
     */
    private String userId;
    /**
     * 合作伙伴Id
     */
    private String partnerId;
}