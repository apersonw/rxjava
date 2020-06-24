package org.rxjava.third.tencent.miniapp.api.impl;

import org.rxjava.third.tencent.miniapp.api.WxMaPluginService;
import org.rxjava.third.tencent.miniapp.api.WxMaService;
import org.rxjava.third.tencent.miniapp.bean.WxMaPluginListResult;
import org.rxjava.third.tencent.miniapp.util.json.WxMaGsonBuilder;
import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.common.error.WxErrorException;

import java.util.Map;

@AllArgsConstructor
public class WxMaPluginServiceImpl implements WxMaPluginService {
  private WxMaService wxMaService;

  @Override
  public void applyPlugin(String pluginAppId, String reason) throws WxErrorException {
    Map<String, String> params = ImmutableMap.of("action", "apply",
      "plugin_appid", pluginAppId,
      "reason", reason);

    this.wxMaService.post(PLUGIN_URL, WxMaGsonBuilder.create().toJson(params));
  }

  @Override
  public WxMaPluginListResult getPluginList() throws WxErrorException {
    Map<String, String> params = ImmutableMap.of("action", "list");

    String responseContent = this.wxMaService.post(PLUGIN_URL, WxMaGsonBuilder.create().toJson(params));
    return WxMaPluginListResult.fromJson(responseContent);
  }

  @Override
  public void unbindPlugin(String pluginAppId) throws WxErrorException {
    Map<String, String> params = ImmutableMap.of("action", "unbind", "plugin_appid", pluginAppId);
    this.wxMaService.post(PLUGIN_URL, WxMaGsonBuilder.create().toJson(params));
  }

  @Override
  public void updatePlugin(String pluginAppId, String userVersion) throws WxErrorException {
    Map<String, String> params = ImmutableMap.of("action", "update",
      "plugin_appid", pluginAppId,
      "user_version", userVersion);

    this.wxMaService.post(PLUGIN_URL, WxMaGsonBuilder.create().toJson(params));
  }
}
