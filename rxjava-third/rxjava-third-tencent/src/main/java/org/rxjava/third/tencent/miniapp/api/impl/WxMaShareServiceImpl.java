package org.rxjava.third.tencent.miniapp.api.impl;

import org.rxjava.third.tencent.miniapp.api.WxMaService;
import org.rxjava.third.tencent.miniapp.api.WxMaShareService;
import org.rxjava.third.tencent.miniapp.bean.WxMaShareInfo;
import org.rxjava.third.tencent.miniapp.util.crypt.WxMaCryptUtils;
import lombok.AllArgsConstructor;

/**
 * @author zhfish
 */
@AllArgsConstructor
public class WxMaShareServiceImpl implements WxMaShareService {
  private WxMaService service;

  @Override
  public WxMaShareInfo getShareInfo(String sessionKey, String encryptedData, String ivStr) {
    return WxMaShareInfo.fromJson(WxMaCryptUtils.decrypt(sessionKey, encryptedData, ivStr));

  }
}
