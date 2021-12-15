package top.rxjava.third.weixin.miniapp.api;

import com.google.gson.JsonObject;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.miniapp.bean.*;

/**
 * 消息发送接口
 */
public interface WxMaMsgService {
    String KEFU_MESSAGE_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    String TEMPLATE_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
    String SUBSCRIBE_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send";
    String UNIFORM_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send";
    String ACTIVITY_ID_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/activityid/create";
    String UPDATABLE_MSG_SEND_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/updatablemsg/send";

    /**
     * 发送客服消息
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/customerServiceMessage.send.html">发送客服消息</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     *
     * @param message 客服消息
     * @return .
     * @throws WxErrorException .
     */
    boolean sendKefuMsg(WxMaKefuMessage message) throws WxErrorException;

    /**
     * 发送模板消息
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/templateMessage.send.html">发送模板消息</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
     * 小程序模板消息接口将于2020年1月10日下线，开发者可使用订阅消息功能
     *
     * @param templateMessage 模版消息
     * @throws WxErrorException .
     */
    @Deprecated
    void sendTemplateMsg(WxMaTemplateMessage templateMessage) throws WxErrorException;

    /**
     * 发送订阅消息
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
     *
     * @param subscribeMessage 订阅消息
     * @throws WxErrorException .
     */
    void sendSubscribeMsg(WxMaSubscribeMessage subscribeMessage) throws WxErrorException;

    /**
     * 下发小程序和公众号统一的服务消息
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html">下发小程序和公众号统一的服务消息</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     *
     * @param uniformMessage 消息
     * @throws WxErrorException .
     */
    void sendUniformMsg(WxMaUniformMessage uniformMessage) throws WxErrorException;

    /**
     * 创建被分享动态消息的 activity_id.
     * 动态消息: https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/share/updatable-message.html
     * <p>
     * 文档地址：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/updatable-message/updatableMessage.createActivityId.html
     * 接口地址：GET https://api.weixin.qq.com/cgi-bin/message/wxopen/activityid/create?access_token=ACCESS_TOKEN
     *
     * @return .
     * @throws WxErrorException .
     */
    JsonObject createUpdatableMessageActivityId() throws WxErrorException;

    /**
     * 修改被分享的动态消息.
     * 动态消息: https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/share/updatable-message.html
     * <p>
     * 文档地址：https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/updatable-message/updatableMessage.setUpdatableMsg.html
     * 接口地址：POST https://api.weixin.qq.com/cgi-bin/message/wxopen/activityid/create?access_token=ACCESS_TOKEN
     *
     * @param msg 动态消息
     * @throws WxErrorException .
     */
    void setUpdatableMsg(WxMaUpdatableMsg msg) throws WxErrorException;
}
