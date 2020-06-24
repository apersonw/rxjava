package org.rxjava.third.tencent.miniapp.api;

import org.rxjava.third.tencent.common.bean.WxJsapiSignature;
import org.rxjava.third.tencent.common.error.WxErrorException;

/**
 * <pre>
 *  jsapi相关接口
 *  Created by BinaryWang on 2018/8/5.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
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
   * <pre>
   * 获得卡券api_ticket
   * 获得时会检查apiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   * </pre>
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
   * <pre>
   * 获得jsapi_ticket
   * 获得时会检查jsapiToken是否过期，如果过期了，那么就刷新一下，否则就什么都不干
   *
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   * </pre>
   *
   * @param forceRefresh 强制刷新
   */
  String getJsapiTicket(boolean forceRefresh) throws WxErrorException;

  /**
   * <pre>
   * 创建调用jsapi时所需要的签名
   *
   * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115&token=&lang=zh_CN
   * </pre>
   */
  WxJsapiSignature createJsapiSignature(String url) throws WxErrorException;

}
