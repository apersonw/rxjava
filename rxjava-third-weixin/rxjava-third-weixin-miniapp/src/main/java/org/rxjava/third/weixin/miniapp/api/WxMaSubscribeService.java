package top.rxjava.third.weixin.miniapp.api;

import lombok.Data;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.miniapp.bean.template.WxMaPubTemplateTitleListResult;

import java.util.List;

/**
 * 订阅消息类
 */
public interface WxMaSubscribeService {
    /**
     * 获取模板标题下的关键词列表.
     */
    String GET_PUB_TEMPLATE_TITLE_LIST_URL = "https://api.weixin.qq.com/wxaapi/newtmpl/getpubtemplatetitles";

    /**
     * 获取模板标题下的关键词列表.
     */
    String GET_PUB_TEMPLATE_KEY_WORDS_BY_ID_URL = "https://api.weixin.qq.com/wxaapi/newtmpl/getpubtemplatekeywords";

    /**
     * 组合模板并添加至帐号下的个人模板库.
     */
    String TEMPLATE_ADD_URL = "https://api.weixin.qq.com/wxaapi/newtmpl/addtemplate";

    /**
     * 获取当前帐号下的个人模板列表.
     */
    String TEMPLATE_LIST_URL = "https://api.weixin.qq.com/wxaapi/newtmpl/gettemplate";

    /**
     * 删除帐号下的某个模板.
     */
    String TEMPLATE_DEL_URL = "https://api.weixin.qq.com/wxaapi/newtmpl/deltemplate";

    /**
     * 获取小程序账号的类目
     */
    String GET_CATEGORY_URL = "https://api.weixin.qq.com/wxaapi/newtmpl/getcategory";

    /**
     * 获取帐号所属类目下的公共模板标题
     * <p>
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.getPubTemplateTitleList.html">获取帐号所属类目下的公共模板标题</a>
     * 接口url格式: https://api.weixin.qq.com/wxaapi/newtmpl/getpubtemplatetitles?access_token=ACCESS_TOKEN
     *
     * @param ids   类目 id，多个用逗号隔开
     * @param limit 用于分页，表示拉取 limit 条记录。最大为 30。
     * @param start 用于分页，表示从 start 开始。从 0 开始计数。
     * @return .
     * @throws WxErrorException .
     */
    WxMaPubTemplateTitleListResult getPubTemplateTitleList(String[] ids, int start, int limit) throws WxErrorException;

    /**
     * 获取模板库某个模板标题下关键词库
     * <p>
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.getPubTemplateKeyWordsById.html">获取模板标题下的关键词列表</a>
     * 接口url格式: GET https://api.weixin.qq.com/wxaapi/newtmpl/getpubtemplatekeywords?access_token=ACCESS_TOKEN
     *
     * @param id 模板标题 id，可通过接口获取
     * @return .
     * @throws WxErrorException .
     */
    List<PubTemplateKeyword> getPubTemplateKeyWordsById(String id) throws WxErrorException;

    /**
     * 组合模板并添加至帐号下的个人模板库
     * <p>
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.addTemplate.html">获取小程序模板库标题列表</a>
     * 接口url格式: POST https://api.weixin.qq.com/wxaapi/newtmpl/addtemplate?access_token=ACCESS_TOKEN
     *
     * @param id            模板标题 id，可通过接口获取，也可登录小程序后台查看获取
     * @param keywordIdList 模板关键词列表
     * @param sceneDesc     服务场景描述，15个字以内
     * @return 添加至帐号下的模板id，发送小程序订阅消息时所需
     * @throws WxErrorException .
     */
    String addTemplate(String id, List<Integer> keywordIdList, String sceneDesc) throws WxErrorException;

    /**
     * 获取当前帐号下的个人模板列表
     * <p>
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.getTemplateList.html">获取当前帐号下的个人模板列表</a>
     * 接口url格式: GET https://api.weixin.qq.com/wxaapi/newtmpl/gettemplate?access_token=ACCESS_TOKEN
     *
     * @return .
     * @throws WxErrorException .
     */
    List<TemplateInfo> getTemplateList() throws WxErrorException;

    /**
     * 删除帐号下的某个模板
     * <p>
     * 详情请见: <a href="https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.deleteTemplate.html">删除帐号下的个人模板</a>
     * 接口url格式: POST https://api.weixin.qq.com/wxaapi/newtmpl/deltemplate?access_token=ACCESS_TOKEN
     *
     * @param templateId 要删除的模板id
     * @return 删除是否成功
     * @throws WxErrorException .
     */
    boolean delTemplate(String templateId) throws WxErrorException;

    /**
     * 获取小程序账号的类目
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.getCategory.html
     * GET https://api.weixin.qq.com/wxaapi/newtmpl/getcategory?access_token=ACCESS_TOKEN
     *
     * @return .
     * @throws WxErrorException .
     */
    List<CategoryData> getCategory() throws WxErrorException;

    @Data
    class CategoryData {
        int id;
        String name;
    }

    @Data
    class TemplateInfo {
        private String priTmplId;
        private String title;
        private String content;
        private String example;
        private int type;
    }

    @Data
    class PubTemplateKeyword {
        private int kid;
        private String name;
        private String example;
        private String rule;
    }
}
