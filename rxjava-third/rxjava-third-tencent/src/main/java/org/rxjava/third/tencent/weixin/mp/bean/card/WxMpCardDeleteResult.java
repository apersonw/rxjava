package org.rxjava.third.tencent.weixin.mp.bean.card;

import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

/**
 * 删除卡券结果.
 */
public class WxMpCardDeleteResult extends BaseWxMpCardResult {
    private static final long serialVersionUID = -4367717540650523290L;

    public static WxMpCardDeleteResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpCardDeleteResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
