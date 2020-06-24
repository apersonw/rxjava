package org.rxjava.third.tencent.weixin.mp.api.impl;

import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpShakeService;
import org.rxjava.third.tencent.weixin.mp.bean.WxMpShakeInfoResult;
import org.rxjava.third.tencent.weixin.mp.bean.WxMpShakeQuery;
import org.rxjava.third.tencent.weixin.mp.bean.shake.*;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.ShakeAround.*;

/**
 */
@RequiredArgsConstructor
public class WxMpShakeServiceImpl implements WxMpShakeService {
    private final WxMpService wxMpService;

    @Override
    public WxMpShakeInfoResult getShakeInfo(WxMpShakeQuery wxMpShakeQuery) throws WxErrorException {
        String postData = wxMpShakeQuery.toJsonString();
        String responseContent = this.wxMpService.post(SHAKEAROUND_USER_GETSHAKEINFO, postData);
        return WxMpShakeInfoResult.fromJson(responseContent);
    }

    @Override
    public WxMpShakeAroundPageAddResult pageAdd(WxMpShakeAroundPageAddQuery shakeAroundPageAddQuery)
            throws WxErrorException {
        String postData = shakeAroundPageAddQuery.toJsonString();
        String responseContent = this.wxMpService.post(SHAKEAROUND_PAGE_ADD, postData);
        return WxMpShakeAroundPageAddResult.fromJson(responseContent);
    }

    @Override
    public WxError deviceBindPageQuery(WxMpShakeAroundDeviceBindPageQuery shakeAroundDeviceBindPageQuery)
            throws WxErrorException {
        String postData = shakeAroundDeviceBindPageQuery.toJsonString();
        String responseContent = this.wxMpService.post(SHAKEAROUND_DEVICE_BINDPAGE, postData);
        return WxError.fromJson(responseContent, WxType.MP);
    }

    @Override
    public WxMpShakeAroundRelationSearchResult relationSearch(WxMpShakeAroundRelationSearchQuery searchQuery)
            throws WxErrorException {
        String postData = searchQuery.toJsonString();
        String responseContent = this.wxMpService.post(SHAKEAROUND_RELATION_SEARCH, postData);
        return WxMpShakeAroundRelationSearchResult.fromJson(responseContent);
    }
}
