package org.rxjava.third.weixin.miniapp.api.impl;

import lombok.AllArgsConstructor;
import org.rxjava.third.weixin.miniapp.api.WxMaService;
import org.rxjava.third.weixin.miniapp.api.WxMaShareService;
import org.rxjava.third.weixin.miniapp.bean.WxMaShareInfo;
import org.rxjava.third.weixin.miniapp.util.crypt.WxMaCryptUtils;

/**
 */
@AllArgsConstructor
public class WxMaShareServiceImpl implements WxMaShareService {
    private WxMaService service;

    @Override
    public WxMaShareInfo getShareInfo(String sessionKey, String encryptedData, String ivStr) {
        return WxMaShareInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));

    }
}