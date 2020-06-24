package org.rxjava.third.tencent.weixin.mp.api.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpDeviceService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.bean.device.*;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.Device.*;

/**
 * Created by keungtung on 10/12/2016.
 *
 * @author keungtung
 */
@Slf4j
@RequiredArgsConstructor
public class WxMpDeviceServiceImpl implements WxMpDeviceService {
    private final WxMpService wxMpService;

    @Override
    public TransMsgResp transMsg(WxDeviceMsg msg) throws WxErrorException {
        String response = this.wxMpService.post(DEVICE_TRANSMSG, msg.toJson());
        return TransMsgResp.fromJson(response);
    }

    @Override
    public WxDeviceQrCodeResult getQrCode(String productId) throws WxErrorException {
        String response = this.wxMpService.get(DEVICE_GETQRCODE, "product_id=" + productId);
        return WxDeviceQrCodeResult.fromJson(response);
    }

    @Override
    public WxDeviceAuthorizeResult authorize(WxDeviceAuthorize wxDeviceAuthorize) throws WxErrorException {
        String response = this.wxMpService.post(DEVICE_AUTHORIZE_DEVICE, wxDeviceAuthorize.toJson());
        return WxDeviceAuthorizeResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult bind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(DEVICE_BIND, wxDeviceBind.toJson());
        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult compelBind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(DEVICE_COMPEL_BIND, wxDeviceBind.toJson());
        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult unbind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(DEVICE_UNBIND, wxDeviceBind.toJson());
        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceBindResult compelUnbind(WxDeviceBind wxDeviceBind) throws WxErrorException {
        String response = this.wxMpService.post(DEVICE_COMPEL_UNBIND, wxDeviceBind.toJson());
        return WxDeviceBindResult.fromJson(response);
    }

    @Override
    public WxDeviceOpenIdResult getOpenId(String deviceType, String deviceId) throws WxErrorException {
        String response = this.wxMpService.get(DEVICE_GET_OPENID, "device_type=" + deviceType + "&device_id=" + deviceId);
        return WxDeviceOpenIdResult.fromJson(response);
    }

    @Override
    public WxDeviceBindDeviceResult getBindDevice(String openId) throws WxErrorException {
        String response = this.wxMpService.get(DEVICE_GET_BIND_DEVICE, "openid=" + openId);
        return WxDeviceBindDeviceResult.fromJson(response);
    }
}
