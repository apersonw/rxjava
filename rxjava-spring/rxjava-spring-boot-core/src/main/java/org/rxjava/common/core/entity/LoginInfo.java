package org.rxjava.common.core.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @author happy 2019-04-14 14:00
 * 登陆信息
 */
@Data
public class LoginInfo {
    /**
     * 用户Id（用户则代表userId，管理员代表为managerId）
     */
    private String userId;
    /**
     * 账号类型
     */
    private String identityType;
    /**
     * 用户授权Id
     */
    private String userAuthId;
    /**
     * 是否禁止访问
     */
    private boolean forbidden = false;
    /**
     * 登陆类型
     */
    private String loginType;
}
