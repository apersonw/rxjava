package org.rxjava.third.weixin.mp.api;

import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.bean.template.WxMpTemplate;
import org.rxjava.third.weixin.mp.bean.template.WxMpTemplateIndustry;
import org.rxjava.third.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.List;

/**
 * 模板消息接口
 * http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
 */
public interface WxMpTemplateMsgService {
    /**
     * 设置所属行业
     * 官方文档中暂未告知响应内容
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     *
     * @param wxMpIndustry 行业信息
     * @return 是否成功
     * @throws WxErrorException .
     */
    boolean setIndustry(WxMpTemplateIndustry wxMpIndustry) throws WxErrorException;

    /***
     *
     * 获取设置的行业信息
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     *
     *
     * @return wxMpIndustry
     * @throws WxErrorException .
     */
    WxMpTemplateIndustry getIndustry() throws WxErrorException;

    /**
     * 发送模板消息
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     *
     * @param templateMessage 模板消息
     * @return 消息Id
     * @throws WxErrorException .
     */
    String sendTemplateMsg(WxMpTemplateMessage templateMessage) throws WxErrorException;

    /**
     * 获得模板ID
     * 从行业模板库选择模板到帐号后台，获得模板ID的过程可在MP中完成
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN
     *
     * @param shortTemplateId 模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
     * @return templateId 模板Id
     * @throws WxErrorException .
     */
    String addTemplate(String shortTemplateId) throws WxErrorException;

    /**
     * 获取模板列表
     * 获取已添加至帐号下所有模板列表，可在MP中查看模板列表信息，为方便第三方开发者，提供通过接口调用的方式来获取帐号下所有模板信息
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=ACCESS_TOKEN
     *
     * @return templateId 模板Id
     * @throws WxErrorException .
     */
    List<WxMpTemplate> getAllPrivateTemplate() throws WxErrorException;

    /**
     * 删除模板
     * 删除模板可在MP中完成，为方便第三方开发者，提供通过接口调用的方式来删除某帐号下的模板
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277&token=&lang=zh_CN
     * 接口地址格式：https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=ACCESS_TOKEN
     *
     * @param templateId 模板Id
     * @return .
     * @throws WxErrorException .
     */
    boolean delPrivateTemplate(String templateId) throws WxErrorException;
}
