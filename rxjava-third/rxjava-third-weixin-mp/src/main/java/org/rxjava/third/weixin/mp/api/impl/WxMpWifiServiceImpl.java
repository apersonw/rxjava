package org.rxjava.third.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.api.WxMpService;
import org.rxjava.third.weixin.mp.api.WxMpWifiService;
import org.rxjava.third.weixin.mp.bean.wifi.WxMpWifiShopDataResult;
import org.rxjava.third.weixin.mp.bean.wifi.WxMpWifiShopListResult;

import static org.rxjava.third.weixin.mp.enums.WxMpApiUrl.Wifi.*;

/**
 */
@RequiredArgsConstructor
public class WxMpWifiServiceImpl implements WxMpWifiService {
    private final WxMpService wxMpService;

    @Override
    public WxMpWifiShopListResult listShop(int pageIndex, int pageSize) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("pageindex", pageIndex);
        json.addProperty("pagesize", pageSize);
        final String result = this.wxMpService.post(BIZWIFI_SHOP_LIST, json.toString());
        return WxMpWifiShopListResult.fromJson(result);
    }

    @Override
    public WxMpWifiShopDataResult getShopWifiInfo(int shopId) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("shop_id", shopId);
        return WxMpWifiShopDataResult.fromJson(this.wxMpService.post(BIZWIFI_SHOP_GET, json.toString()));
    }

    @Override
    public boolean updateShopWifiInfo(int shopId, String oldSsid, String ssid, String password) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("shop_id", shopId);
        json.addProperty("old_ssid", oldSsid);
        json.addProperty("ssid", ssid);
        if (password != null) {
            json.addProperty("password", password);
        }
        try {
            this.wxMpService.post(BIZWIFI_SHOP_UPDATE, json.toString());
            return true;
        } catch (WxErrorException e) {
            throw e;
        }
    }
}
