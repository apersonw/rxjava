package org.rxjava.third.tencent.weixin.mp.bean.membercard;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 用于 `7 更新会员信息` 的接口调用后的返回结果
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1451025283
 * <p>
 * <p>
 * (mgcnrx11@gmail.com)
 *
 * @version 2017/7/15
 */
@Data
public class WxMpMemberCardUpdateResult implements Serializable {

    private static final long serialVersionUID = 9084886191442098311L;

    private String errorCode;

    private String errorMsg;

    private String openId;

    private Integer resultBonus;

    private Double resultBalance;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static WxMpMemberCardUpdateResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMemberCardUpdateResult.class);
    }
}
