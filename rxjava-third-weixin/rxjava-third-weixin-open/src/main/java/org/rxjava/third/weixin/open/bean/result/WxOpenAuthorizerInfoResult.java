package top.rxjava.third.weixin.open.bean.result;

import lombok.Data;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizationInfo;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizerInfo;

import java.io.Serializable;

/**
 */
@Data
public class WxOpenAuthorizerInfoResult implements Serializable {
    private static final long serialVersionUID = 3166298050833019785L;

    private WxOpenAuthorizationInfo authorizationInfo;
    private WxOpenAuthorizerInfo authorizerInfo;

    public boolean isMiniProgram() {
        return authorizerInfo != null && authorizerInfo.getMiniProgramInfo() != null;
    }
}
