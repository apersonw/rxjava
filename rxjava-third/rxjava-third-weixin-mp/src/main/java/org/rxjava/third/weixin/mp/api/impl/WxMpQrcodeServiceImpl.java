package org.rxjava.third.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.api.WxMpQrcodeService;
import org.rxjava.third.weixin.mp.api.WxMpService;
import org.rxjava.third.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.rxjava.third.weixin.mp.util.requestexecuter.qrcode.QrCodeRequestExecutor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.rxjava.third.weixin.mp.enums.WxMpApiUrl.Qrcode.*;

/**
 */
@RequiredArgsConstructor
public class WxMpQrcodeServiceImpl implements WxMpQrcodeService {
    private final WxMpService wxMpService;

    @Override
    public WxMpQrCodeTicket qrCodeCreateTmpTicket(int sceneId, Integer expireSeconds) throws WxErrorException {
        if (sceneId == 0) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("临时二维码场景值不能为0！").build());
        }

        return this.createQrCode("QR_SCENE", null, sceneId, expireSeconds);
    }

    @Override
    public WxMpQrCodeTicket qrCodeCreateTmpTicket(String sceneStr, Integer expireSeconds) throws WxErrorException {
        if (StringUtils.isBlank(sceneStr)) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("临时二维码场景值不能为空！").build());
        }

        return this.createQrCode("QR_STR_SCENE", sceneStr, null, expireSeconds);
    }

    private WxMpQrCodeTicket createQrCode(String actionName, String sceneStr, Integer sceneId, Integer expireSeconds)
            throws WxErrorException {
        //expireSeconds 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天），此字段如果不填，则默认有效期为30秒。
        if (expireSeconds != null && expireSeconds > 2592000) {
            throw new WxErrorException(WxError.builder().errorCode(-1)
                    .errorMsg("临时二维码有效时间最大不能超过2592000（即30天）！").build());
        }

        if (expireSeconds == null) {
            expireSeconds = 30;
        }

        return this.getQrCodeTicket(actionName, sceneStr, sceneId, expireSeconds);
    }

    private WxMpQrCodeTicket getQrCodeTicket(String actionName, String sceneStr, Integer sceneId, Integer expireSeconds)
            throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("action_name", actionName);
        if (expireSeconds != null) {
            json.addProperty("expire_seconds", expireSeconds);
        }

        JsonObject actionInfo = new JsonObject();
        JsonObject scene = new JsonObject();
        if (sceneStr != null) {
            scene.addProperty("scene_str", sceneStr);
        } else if (sceneId != null) {
            scene.addProperty("scene_id", sceneId);
        }

        actionInfo.add("scene", scene);
        json.add("action_info", actionInfo);
        String responseContent = this.wxMpService.post(QRCODE_CREATE, json.toString());
        return WxMpQrCodeTicket.fromJson(responseContent);
    }

    @Override
    public WxMpQrCodeTicket qrCodeCreateLastTicket(int sceneId) throws WxErrorException {
        if (sceneId < 1 || sceneId > 100000) {
            throw new WxErrorException(WxError.builder().errorCode(-1)
                    .errorMsg("永久二维码的场景值目前只支持1--100000！")
                    .build());
        }

        return this.getQrCodeTicket("QR_LIMIT_SCENE", null, sceneId, null);
    }

    @Override
    public WxMpQrCodeTicket qrCodeCreateLastTicket(String sceneStr) throws WxErrorException {
        return this.getQrCodeTicket("QR_LIMIT_STR_SCENE", sceneStr, null, null);
    }

    @Override
    public File qrCodePicture(WxMpQrCodeTicket ticket) throws WxErrorException {
        return this.wxMpService.execute(QrCodeRequestExecutor.create(this.wxMpService.getRequestHttp()), SHOW_QRCODE, ticket);
    }

    @Override
    public String qrCodePictureUrl(String ticket, boolean needShortUrl) throws WxErrorException {
        try {
            String resultUrl = String.format(SHOW_QRCODE_WITH_TICKET.getUrl(this.wxMpService.getWxMpConfigStorage()),
                    URLEncoder.encode(ticket, StandardCharsets.UTF_8.name()));
            if (needShortUrl) {
                return this.wxMpService.shortUrl(resultUrl);
            }

            return resultUrl;
        } catch (UnsupportedEncodingException e) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg(e.getMessage()).build());
        }
    }

    @Override
    public String qrCodePictureUrl(String ticket) throws WxErrorException {
        return this.qrCodePictureUrl(ticket, false);
    }

}
