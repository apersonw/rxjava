package org.rxjava.third.tencent.weixin.miniapp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaMsgService;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaService;
import org.rxjava.third.tencent.weixin.miniapp.bean.*;
import org.rxjava.third.tencent.weixin.miniapp.constant.WxMaConstants;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@AllArgsConstructor
public class WxMaMsgServiceImpl implements WxMaMsgService {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private WxMaService wxMaService;

    @Override
    public boolean sendKefuMsg(WxMaKefuMessage message) throws WxErrorException {
        String responseContent = this.wxMaService.post(KEFU_MESSAGE_SEND_URL, message.toJson());
        return responseContent != null;
    }

    @Override
    public void sendTemplateMsg(WxMaTemplateMessage templateMessage) throws WxErrorException {
        String responseContent = this.wxMaService.post(TEMPLATE_MSG_SEND_URL, templateMessage.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get(WxMaConstants.ERRCODE).getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
        }
    }

    @Override
    public void sendSubscribeMsg(WxMaSubscribeMessage subscribeMessage) throws WxErrorException {
        String responseContent = this.wxMaService.post(SUBSCRIBE_MSG_SEND_URL, subscribeMessage.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get(WxMaConstants.ERRCODE).getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
        }
    }

    @Override
    public void sendUniformMsg(WxMaUniformMessage uniformMessage) throws WxErrorException {
        String responseContent = this.wxMaService.post(UNIFORM_MSG_SEND_URL, uniformMessage.toJson());
        JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get(WxMaConstants.ERRCODE).getAsInt() != 0) {
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
        }
    }

    @Override
    public JsonObject createUpdatableMessageActivityId() throws WxErrorException {
        final String responseContent = this.wxMaService.get(ACTIVITY_ID_CREATE_URL, null);
        return JSON_PARSER.parse(responseContent).getAsJsonObject();
    }

    @Override
    public void setUpdatableMsg(WxMaUpdatableMsg msg) throws WxErrorException {
        this.wxMaService.post(UPDATABLE_MSG_SEND_URL, WxMaGsonBuilder.create().toJson(msg));
    }

}
