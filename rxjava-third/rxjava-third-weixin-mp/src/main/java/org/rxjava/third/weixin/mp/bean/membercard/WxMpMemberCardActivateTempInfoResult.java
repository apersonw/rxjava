package org.rxjava.third.weixin.mp.bean.membercard;

import lombok.Data;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;


/**
 */
@Data
public class WxMpMemberCardActivateTempInfoResult {

    private String errorCode;

    private String errorMsg;

    private MemberCardUserInfo userInfo;

    public static WxMpMemberCardActivateTempInfoResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpMemberCardActivateTempInfoResult.class);
    }

}