package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.gson.JsonParser;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpAiOpenService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.enums.AiLangType;
import org.rxjava.third.tencent.weixin.mp.util.requestexecuter.voice.VoiceUploadRequestExecutor;

import java.io.File;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.AiOpen.*;

/**
 * Created by BinaryWang on 2018/6/9.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequiredArgsConstructor
public class WxMpAiOpenServiceImpl implements WxMpAiOpenService {
    private static final JsonParser JSON_PARSER = new JsonParser();
    private final WxMpService wxMpService;

    @Override
    public void uploadVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException {
        if (lang == null) {
            lang = AiLangType.zh_CN;
        }

        this.wxMpService.execute(VoiceUploadRequestExecutor.create(this.wxMpService.getRequestHttp()),
                String.format(VOICE_UPLOAD_URL.getUrl(this.wxMpService.getWxMpConfigStorage()), "mp3", voiceId, lang.getCode()),
                voiceFile);
    }

    @Override
    public String recogniseVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException {
        this.uploadVoice(voiceId, lang, voiceFile);
        return this.queryRecognitionResult(voiceId, lang);
    }

    @Override
    public String translate(AiLangType langFrom, AiLangType langTo, String content) throws WxErrorException {
        String response = this.wxMpService.post(String.format(TRANSLATE_URL.getUrl(this.wxMpService.getWxMpConfigStorage()),
                langFrom.getCode(), langTo.getCode()), content);

        WxError error = WxError.fromJson(response, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }

        return JSON_PARSER.parse(response).getAsJsonObject().get("to_content").getAsString();
    }

    @Override
    public String queryRecognitionResult(String voiceId, AiLangType lang) throws WxErrorException {
        if (lang == null) {
            lang = AiLangType.zh_CN;
        }

        final String response = this.wxMpService.get(VOICE_QUERY_RESULT_URL,
                String.format("voice_id=%s&lang=%s", voiceId, lang.getCode()));
        WxError error = WxError.fromJson(response, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }

        return JSON_PARSER.parse(response).getAsJsonObject().get("result").getAsString();
    }
}
