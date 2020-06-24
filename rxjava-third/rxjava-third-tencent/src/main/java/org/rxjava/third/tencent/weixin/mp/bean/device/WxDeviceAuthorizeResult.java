package org.rxjava.third.tencent.weixin.mp.bean.device;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.rxjava.third.tencent.weixin.common.util.json.WxGsonBuilder;

import java.util.List;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceAuthorizeResult extends AbstractDeviceBean {
    private static final long serialVersionUID = 9105294570912249811L;

    private List<BaseResp> resp;

    public static WxDeviceAuthorizeResult fromJson(String response) {
        return WxGsonBuilder.create().fromJson(response, WxDeviceAuthorizeResult.class);
    }

}
