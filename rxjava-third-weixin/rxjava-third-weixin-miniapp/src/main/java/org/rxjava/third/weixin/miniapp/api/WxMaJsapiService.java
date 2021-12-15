package top.rxjava.third.weixin.miniapp.api;

import top.rxjava.third.weixin.common.bean.WxJsapiSignature;
import top.rxjava.third.weixin.common.error.WxErrorException;

/**
 * jsapi相关接口
 */
public interface WxMaJsapiService {
    /**
     * 获得jsapi_ticket的url
     */
    String GET_JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /**
     * 获得卡券api_ticket,不强制刷新api_ticket
     *
     * @see #getJsapiTicket(boolean)
     */
    String getCardApiTicket() throws WxErrorException;

    /**
     * 获得卡券api_ticket
     * 获得时会检查apiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
     * <p>
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
     *
     * @param forceRefresh 强制刷新
     */
    String getCardApiTicket(boolean forceRefresh) throws WxErrorException;

    /**
     * 获得jsapi_ticket,不强制刷新jsapi_ticket
     *
     * @see #getJsapiTicket(boolean)
     */
    String getJsapiTicket() throws WxErrorException;

    /**
     * 获得jsapi_ticket
     * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
     * <p>
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
     *
     * @param forceRefresh 强制刷新
     */
    String getJsapiTicket(boolean forceRefresh) throws WxErrorException;

    /**
     * 创建调用jsapi时所需要的签名
     * <p>
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
     */
    WxJsapiSignature createJsapiSignature(String url) throws WxErrorException;

}
