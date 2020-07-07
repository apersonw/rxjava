package org.rxjava.third.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.api.WxMpMassMessageService;
import org.rxjava.third.weixin.mp.api.WxMpService;
import org.rxjava.third.weixin.mp.bean.*;
import org.rxjava.third.weixin.mp.bean.result.WxMpMassGetResult;
import org.rxjava.third.weixin.mp.bean.result.WxMpMassSendResult;
import org.rxjava.third.weixin.mp.bean.result.WxMpMassSpeedGetResult;
import org.rxjava.third.weixin.mp.bean.result.WxMpMassUploadResult;

import static org.rxjava.third.weixin.mp.enums.WxMpApiUrl.MassMessage;

/**
 * 群发消息服务类
 */
@Slf4j
@RequiredArgsConstructor
public class WxMpMassMessageServiceImpl implements WxMpMassMessageService {
    private final WxMpService wxMpService;

    @Override
    public WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException {
        String responseContent = this.wxMpService.post(MassMessage.MEDIA_UPLOAD_NEWS_URL, news.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException {
        String responseContent = this.wxMpService.post(MassMessage.MEDIA_UPLOAD_VIDEO_URL, video.toJson());
        return WxMpMassUploadResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massGroupMessageSend(WxMpMassTagMessage message) throws WxErrorException {
        String responseContent = this.wxMpService.post(MassMessage.MESSAGE_MASS_SENDALL_URL, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException {
        String responseContent = this.wxMpService.post(MassMessage.MESSAGE_MASS_SEND_URL, message.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public WxMpMassSendResult massMessagePreview(WxMpMassPreviewMessage wxMpMassPreviewMessage) throws WxErrorException {
        String responseContent = this.wxMpService.post(MassMessage.MESSAGE_MASS_PREVIEW_URL, wxMpMassPreviewMessage.toJson());
        return WxMpMassSendResult.fromJson(responseContent);
    }

    @Override
    public void delete(Long msgId, Integer articleIndex) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg_id", msgId);
        jsonObject.addProperty("article_idx", articleIndex);
        this.wxMpService.post(MassMessage.MESSAGE_MASS_DELETE_URL, jsonObject.toString());
    }


    @Override
    public WxMpMassSpeedGetResult messageMassSpeedGet() throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        String response = this.wxMpService.post(MassMessage.MESSAGE_MASS_SPEED_GET_URL, jsonObject.toString());
        return WxMpMassSpeedGetResult.fromJson(response);
    }


    @Override
    public void messageMassSpeedSet(Integer speed) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("speed", speed);
        this.wxMpService.post(MassMessage.MESSAGE_MASS_SPEED_SET_URL, jsonObject.toString());
    }


    @Override
    public WxMpMassGetResult messageMassGet(Long msgId) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg_id", msgId);
        String response = this.wxMpService.post(MassMessage.MESSAGE_MASS_GET_URL, jsonObject.toString());
        return WxMpMassGetResult.fromJson(response);
    }

}
