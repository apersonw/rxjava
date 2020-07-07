package org.rxjava.third.weixin.cp.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用oauth2获取用户信息的结果类
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxCpOauth2UserInfo {
    private String openId;
    private String deviceId;
    private String userId;
    private String userTicket;
    private String expiresIn;
}
