package org.rxjava.third.tencent.weixin.open.bean.result;

import lombok.Data;
import org.rxjava.third.tencent.weixin.open.bean.auth.WxOpenAuthorizationInfo;

import java.io.Serializable;

/**
 * @author <a href="https://github.com/007gzs">007</a>
 */
@Data
public class WxOpenQueryAuthResult implements Serializable {
    private static final long serialVersionUID = 2394736235020206855L;

    private WxOpenAuthorizationInfo authorizationInfo;
}
