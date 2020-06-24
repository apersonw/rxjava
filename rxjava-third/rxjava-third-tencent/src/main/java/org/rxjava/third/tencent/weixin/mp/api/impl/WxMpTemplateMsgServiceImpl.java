package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpTemplateMsgService;
import org.rxjava.third.tencent.weixin.mp.bean.template.WxMpTemplate;
import org.rxjava.third.tencent.weixin.mp.bean.template.WxMpTemplateIndustry;
import org.rxjava.third.tencent.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.List;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.TemplateMsg.*;

/**
 * Created by Binary Wang on 2016-10-14.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequiredArgsConstructor
public class WxMpTemplateMsgServiceImpl implements WxMpTemplateMsgService {
    private static final JsonParser JSON_PARSER = new JsonParser();

    private final WxMpService wxMpService;

    @Override
    public String sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException {
        String responseContent = this.wxMpService.post(MESSAGE_TEMPLATE_SEND, templateMessage.toJson());
        final JsonObject jsonObject = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (jsonObject.get("errcode").getAsInt() == 0) {
            return jsonObject.get("msgid").getAsString();
        }
        throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
    }

    @Override
    public boolean setIndustry(WxMpTemplateIndustry wxMpIndustry) throws WxErrorException {
        if (null == wxMpIndustry.getPrimaryIndustry() || null == wxMpIndustry.getPrimaryIndustry().getCode()
                || null == wxMpIndustry.getSecondIndustry() || null == wxMpIndustry.getSecondIndustry().getCode()) {
            throw new IllegalArgumentException("行业Id不能为空，请核实");
        }

        this.wxMpService.post(TEMPLATE_API_SET_INDUSTRY, wxMpIndustry.toJson());
        return true;
    }

    @Override
    public WxMpTemplateIndustry getIndustry() throws WxErrorException {
        String responseContent = this.wxMpService.get(TEMPLATE_GET_INDUSTRY, null);
        return WxMpTemplateIndustry.fromJson(responseContent);
    }

    @Override
    public String addTemplate(String shortTemplateId) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("template_id_short", shortTemplateId);
        String responseContent = this.wxMpService.post(TEMPLATE_API_ADD_TEMPLATE, jsonObject.toString());
        final JsonObject result = JSON_PARSER.parse(responseContent).getAsJsonObject();
        if (result.get("errcode").getAsInt() == 0) {
            return result.get("template_id").getAsString();
        }

        throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
    }

    @Override
    public List<WxMpTemplate> getAllPrivateTemplate() throws WxErrorException {
        return WxMpTemplate.fromJson(this.wxMpService.get(TEMPLATE_GET_ALL_PRIVATE_TEMPLATE, null));
    }

    @Override
    public boolean delPrivateTemplate(String templateId) throws WxErrorException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("template_id", templateId);
        String responseContent = this.wxMpService.post(TEMPLATE_DEL_PRIVATE_TEMPLATE, jsonObject.toString());
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() == 0) {
            return true;
        }

        throw new WxErrorException(error);
    }

}
