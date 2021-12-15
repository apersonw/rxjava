package top.rxjava.third.weixin.mp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.bean.device.*;

/**
 *
 */
public interface WxMpDeviceService {
    /**
     * 主动发送消息给设备
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-3
     */
    TransMsgResp transMsg(WxDeviceMsg msg) throws WxErrorException;

    /**
     * 获取一组新的deviceid和设备二维码
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-6
     *
     * @param productId 产品id
     * @return 返回WxDeviceQrCodeResult
     */
    WxDeviceQrCodeResult getQrCode(String productId) throws WxErrorException;

    /**
     * 将device id及其属性信息提交公众平台进行授权
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-6
     *
     * @param wxDeviceAuthorize 授权请求对象
     * @return WxDeviceAuthorizeResult
     */
    WxDeviceAuthorizeResult authorize(WxDeviceAuthorize wxDeviceAuthorize) throws WxErrorException;


    /**
     * 第三方后台绑定成功后，通知公众平台
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html/page=3-4-7
     *
     * @param wxDeviceBind 绑定请求对象
     * @return WxDeviceBindResult
     */
    WxDeviceBindResult bind(WxDeviceBind wxDeviceBind) throws WxErrorException;

    /**
     * 强制绑定用户和设备
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-7
     *
     * @param wxDeviceBind 强制绑定请求对象
     * @return WxDeviceBindResult
     */
    WxDeviceBindResult compelBind(WxDeviceBind wxDeviceBind) throws WxErrorException;

    /**
     * 第三方确认用户和设备的解绑操作
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html/page=3-4-7
     *
     * @param wxDeviceBind 绑定请求对象
     * @return WxDeviceBidResult
     */
    WxDeviceBindResult unbind(WxDeviceBind wxDeviceBind) throws WxErrorException;

    /**
     * 强制解绑用户和设备
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-7
     *
     * @param wxDeviceBind 强制解绑请求对象
     * @return WxDeviceBindResult
     */
    WxDeviceBindResult compelUnbind(WxDeviceBind wxDeviceBind) throws WxErrorException;

    /**
     * 通过device type和device id 获取设备主人的openid
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-11
     *
     * @param deviceType 设备类型，目前为"公众账号原始ID"
     * @param deviceId   设备ID
     * @return WxDeviceOpenIdResult
     */
    WxDeviceOpenIdResult getOpenId(String deviceType, String deviceId) throws WxErrorException;

    /**
     * 通过openid获取用户在当前devicetype下绑定的deviceid列表`
     * 详情请见：http://iot.weixin.qq.com/wiki/new/index.html?page=3-4-12
     *
     * @param openId 要查询的用户的openid
     * @return WxDeviceBindDeviceResult
     */
    WxDeviceBindDeviceResult getBindDevice(String openId) throws WxErrorException;
}
