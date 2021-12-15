package top.rxjava.third.weixin.open.bean.result;

import lombok.Data;
import top.rxjava.third.weixin.open.bean.auth.WxOpenAuthorizationInfo;

import java.io.Serializable;

/**
 */
@Data
public class WxOpenQueryAuthResult implements Serializable {
    private static final long serialVersionUID = 2394736235020206855L;

    private WxOpenAuthorizationInfo authorizationInfo;
}
