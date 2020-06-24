package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpUserService;
import org.rxjava.third.tencent.weixin.mp.bean.WxMpUserQuery;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpChangeOpenid;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpUser;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpUserList;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.User.*;

/**
 */
@RequiredArgsConstructor
public class WxMpUserServiceImpl implements WxMpUserService {
    private final WxMpService wxMpService;

    @Override
    public void userUpdateRemark(String openid, String remark) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("openid", openid);
        json.addProperty("remark", remark);
        this.wxMpService.post(USER_INFO_UPDATE_REMARK_URL, json.toString());
    }

    @Override
    public WxMpUser userInfo(String openid) throws WxErrorException {
        return this.userInfo(openid, null);
    }

    @Override
    public WxMpUser userInfo(String openid, String lang) throws WxErrorException {
        lang = lang == null ? "zh_CN" : lang;
        String responseContent = this.wxMpService.get(USER_INFO_URL, "openid=" + openid + "&lang=" + lang);
        return WxMpUser.fromJson(responseContent);
    }

    @Override
    public WxMpUserList userList(String nextOpenid) throws WxErrorException {
        String responseContent = this.wxMpService.get(USER_GET_URL, nextOpenid == null ? null : "next_openid=" + nextOpenid);
        return WxMpUserList.fromJson(responseContent);
    }

    @Override
    public List<WxMpChangeOpenid> changeOpenid(String fromAppid, List<String> openidList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>(2);
        map.put("from_appid", fromAppid);
        map.put("openid_list", openidList);
        String responseContent = this.wxMpService.post(USER_CHANGE_OPENID_URL, WxMpGsonBuilder.create().toJson(map));

        return WxMpChangeOpenid.fromJsonList(responseContent);
    }

    @Override
    public List<WxMpUser> userInfoList(List<String> openidList)
            throws WxErrorException {
        return this.userInfoList(new WxMpUserQuery(openidList));
    }

    @Override
    public List<WxMpUser> userInfoList(WxMpUserQuery userQuery) throws WxErrorException {
        String responseContent = this.wxMpService.post(USER_INFO_BATCH_GET_URL, userQuery.toJsonString());
        return WxMpUser.fromJsonList(responseContent);
    }

}
