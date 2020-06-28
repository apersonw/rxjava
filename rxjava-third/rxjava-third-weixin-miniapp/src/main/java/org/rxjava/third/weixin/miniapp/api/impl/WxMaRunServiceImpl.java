package org.rxjava.third.weixin.miniapp.api.impl;

import lombok.AllArgsConstructor;
import org.rxjava.third.weixin.miniapp.api.WxMaRunService;
import org.rxjava.third.weixin.miniapp.api.WxMaService;
import org.rxjava.third.weixin.miniapp.bean.WxMaRunStepInfo;
import org.rxjava.third.weixin.miniapp.util.crypt.WxMaCryptUtils;

import java.util.List;

/**
 */
@AllArgsConstructor
public class WxMaRunServiceImpl implements WxMaRunService {
    private WxMaService service;

    @Override
    public List<WxMaRunStepInfo> getRunStepInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxMaRunStepInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));
    }
}
