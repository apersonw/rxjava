package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.SimplePostRequestExecutor;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpUserBlacklistService;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpUserBlacklistGetResult;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.UserBlacklist.*;

/**
 */
@RequiredArgsConstructor
public class WxMpUserBlacklistServiceImpl implements WxMpUserBlacklistService {
    private final WxMpService wxMpService;

    @Override
    public WxMpUserBlacklistGetResult getBlacklist(String nextOpenid) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("begin_openid", nextOpenid);
        String responseContent = this.wxMpService.execute(SimplePostRequestExecutor.create(this.wxMpService.getRequestHttp()),
                GETBLACKLIST, jsonObject.toString());
        return WxMpUserBlacklistGetResult.fromJson(responseContent);
    }

    @Override
    public void pushToBlacklist(List<String> openidList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>(2);
        map.put("openid_list", openidList);
        this.wxMpService.execute(SimplePostRequestExecutor.create(this.wxMpService.getRequestHttp()), BATCHBLACKLIST,
                WxMpGsonBuilder.create().toJson(map));
    }

    @Override
    public void pullFromBlacklist(List<String> openidList) throws WxErrorException {
        Map<String, Object> map = new HashMap<>(2);
        map.put("openid_list", openidList);
        this.wxMpService.execute(SimplePostRequestExecutor.create(this.wxMpService.getRequestHttp()), BATCHUNBLACKLIST,
                WxMpGsonBuilder.create().toJson(map));
    }
}
