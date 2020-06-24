package org.rxjava.third.tencent.miniapp.api.impl;

import org.rxjava.third.tencent.miniapp.api.WxMaService;
import org.rxjava.third.tencent.miniapp.api.WxMaSettingService;
import org.rxjava.third.tencent.miniapp.bean.WxMaDomainAction;
import org.rxjava.third.tencent.miniapp.util.json.WxMaGsonBuilder;
import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.common.error.WxErrorException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="https://github.com/charmingoh">Charming</a>
 * @since 2018-04-27 15:46
 */
@AllArgsConstructor
public class WxMaSettingServiceImpl implements WxMaSettingService {
  private WxMaService wxMaService;

  @Override
  public WxMaDomainAction modifyDomain(WxMaDomainAction domainAction) throws WxErrorException {
    String responseContent = this.wxMaService.post(MODIFY_DOMAIN_URL, domainAction.toJson());
    return WxMaDomainAction.fromJson(responseContent);
  }

  @Override
  public WxMaDomainAction setWebViewDomain(WxMaDomainAction domainAction) throws WxErrorException {
    String responseContent = this.wxMaService.post(SET_WEB_VIEW_DOMAIN_URL, domainAction.toJson());
    return WxMaDomainAction.fromJson(responseContent);
  }

  @Override
  public void bindTester(String wechatId) throws WxErrorException {
    Map<String, Object> param = new HashMap<>(1);
    param.put("wechatid", wechatId);
    this.wxMaService.post(BIND_TESTER_URL, WxMaGsonBuilder.create().toJson(param));
  }

  @Override
  public void unbindTester(String wechatId) throws WxErrorException {
    Map<String, Object> param = new HashMap<>(1);
    param.put("wechatid", wechatId);
    this.wxMaService.post(UNBIND_TESTER_URL, WxMaGsonBuilder.create().toJson(param));
  }
}
