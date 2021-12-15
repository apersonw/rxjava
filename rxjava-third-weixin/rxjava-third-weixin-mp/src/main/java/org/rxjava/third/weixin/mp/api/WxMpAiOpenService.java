package top.rxjava.third.weixin.mp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.enums.AiLangType;

import java.io.File;

/**
 * 微信AI开放接口（语音识别，微信翻译）.
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=21516712282KzWVE
 */
public interface WxMpAiOpenService {

    /**
     * 提交语音.
     * http请求方式: POST
     * http://api.weixin.qq.com/cgi-bin/media/voice/addvoicetorecofortext?access_token=ACCESS_TOKEN&format=&voice_id=xxxxxx&lang=zh_CN
     *
     * @param lang      语言，zh_CN 或 en_US，默认中文
     * @param voiceFile 语音文件
     * @param voiceId   语音唯一标识
     */
    void uploadVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException;

    /**
     * 获取语音识别结果.
     * 接口调用请求说明
     * <p>
     * http://api.weixin.qq.com/cgi-bin/media/voice/queryrecoresultfortext?access_token=ACCESS_TOKEN&voice_id=xxxxxx&lang=zh_CN
     * 请注意，添加完文件之后10s内调用这个接口
     *
     * @param lang    语言，zh_CN 或 en_US，默认中文
     * @param voiceId 语音唯一标识
     */
    String queryRecognitionResult(String voiceId, AiLangType lang) throws WxErrorException;

    /**
     * 识别指定语音文件内容.
     * 此方法揉合了前两两个方法：uploadVoice 和 queryRecognitionResult
     *
     * @param lang      语言，zh_CN 或 en_US，默认中文
     * @param voiceFile 语音文件
     * @param voiceId   语音唯一标识
     */
    String recogniseVoice(String voiceId, AiLangType lang, File voiceFile) throws WxErrorException;

    /**
     * 微信翻译.
     * 接口调用请求说明
     * <p>
     * http请求方式: POST
     * http://api.weixin.qq.com/cgi-bin/media/voice/translatecontent?access_token=ACCESS_TOKEN&lfrom=xxx&lto=xxx
     *
     * @param langFrom 源语言，zh_CN 或 en_US
     * @param langTo   目标语言，zh_CN 或 en_US
     * @param content  要翻译的文本内容
     */
    String translate(AiLangType langFrom, AiLangType langTo, String content) throws WxErrorException;
}
