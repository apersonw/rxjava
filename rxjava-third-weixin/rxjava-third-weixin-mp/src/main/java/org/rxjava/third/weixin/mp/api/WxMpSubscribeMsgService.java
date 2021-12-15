package top.rxjava.third.weixin.mp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.bean.subscribe.WxMpSubscribeMessage;

/**
 * 一次性订阅消息接口
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1500374289_66bvB
 */
public interface WxMpSubscribeMsgService {
    /**
     * 构造用户订阅一条模板消息授权的url连接
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1500374289_66bvB
     *
     * @param redirectURI 用户授权完成后的重定向链接，无需urlencode, 方法内会进行encode
     * @param scene       重定向后会带上scene参数，开发者可以填0-10000的整形值，用来标识订阅场景值
     * @param reserved    用于保持请求和回调的状态，授权请后原样带回给第三方 (最多128字节，要求做urlencode)
     * @return url
     */
    String subscribeMsgAuthorizationUrl(String redirectURI, int scene, String reserved);

    /**
     * 发送一次性订阅消息
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1500374289_66bvB
     *
     * @return 消息Id
     */
    boolean sendSubscribeMessage(WxMpSubscribeMessage message) throws WxErrorException;

}
