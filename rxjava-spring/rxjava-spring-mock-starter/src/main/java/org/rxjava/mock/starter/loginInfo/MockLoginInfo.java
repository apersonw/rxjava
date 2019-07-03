package org.rxjava.mock.starter.loginInfo;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author happy 2019-07-03 11:43
 */
@ConfigurationProperties(prefix = "mock")
@Data
public class MockLoginInfo {
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
}
