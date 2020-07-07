package org.rxjava.third.weixin.open.api;

import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.miniapp.bean.WxMaJscode2SessionResult;
import org.rxjava.third.weixin.mp.api.WxMpService;
import org.rxjava.third.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.rxjava.third.weixin.open.bean.WxOpenCreateResult;
import org.rxjava.third.weixin.open.bean.WxOpenGetResult;
import org.rxjava.third.weixin.open.bean.WxOpenMaCodeTemplate;
import org.rxjava.third.weixin.open.bean.message.WxOpenXmlMessage;
import org.rxjava.third.weixin.open.bean.result.*;

import java.util.List;

/**
 */
public interface WxOpenComponentService {
    String API_COMPONENT_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    String API_CREATE_PREAUTHCODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode";
    String API_QUERY_AUTH_URL = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth";
    String API_AUTHORIZER_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token";
    String API_GET_AUTHORIZER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info";
    String API_GET_AUTHORIZER_OPTION_URL = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_option";
    String API_SET_AUTHORIZER_OPTION_URL = "https://api.weixin.qq.com/cgi-bin/component/api_set_authorizer_option";
    String API_GET_AUTHORIZER_LIST = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_list";

    String COMPONENT_LOGIN_PAGE_URL = "https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=xxx&biz_appid=xxx";

    /**
     * 手机端打开授权链接.
     */
    String COMPONENT_MOBILE_LOGIN_PAGE_URL = "https://mp.weixin.qq.com/safe/bindcomponent?action=bindcomponent&no_scan=1&auth_type=3&component_appid=%s&pre_auth_code=%s&redirect_uri=%s&auth_type=xxx&biz_appid=xxx#wechat_redirect";
    String CONNECT_OAUTH2_AUTHORIZE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s&component_appid=%s#wechat_redirect";

    /**
     * 用code换取oauth2的access token.
     */
    String OAUTH2_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/component/access_token?appid=%s&code=%s&grant_type=authorization_code&component_appid=%s";
    /**
     * 刷新oauth2的access token.
     */
    String OAUTH2_REFRESH_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/component/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s&component_appid=%s";

    String MINIAPP_JSCODE_2_SESSION = "https://api.weixin.qq.com/sns/component/jscode2session?appid=%s&js_code=%s&grant_type=authorization_code&component_appid=%s";

    String CREATE_OPEN_URL = "https://api.weixin.qq.com/cgi-bin/open/create";

    String BIND_OPEN_URL = "https://api.weixin.qq.com/cgi-bin/open/bind";

    String UNBIND_OPEN_URL = "https://api.weixin.qq.com/cgi-bin/open/unbind";

    String GET_OPEN_URL = "https://api.weixin.qq.com/cgi-bin/open/get";

    /**
     * 快速创建小程序接口.
     */
    String FAST_REGISTER_WEAPP_URL = "https://api.weixin.qq.com/cgi-bin/component/fastregisterweapp?action=create";
    String FAST_REGISTER_WEAPP_SEARCH_URL = "https://api.weixin.qq.com/cgi-bin/component/fastregisterweapp?action=search";

    WxMpService getWxMpServiceByAppid(String appid);

    /**
     * 获取指定appid的开放平台小程序服务（继承一般小程序服务能力）.
     *
     * @param appid .
     * @return .
     */
    WxOpenMaService getWxMaServiceByAppid(String appid);

    /**
     * 获取指定appid的快速创建的小程序服务.
     *
     * @param appid .
     * @return .
     */
    WxOpenFastMaService getWxFastMaServiceByAppid(String appid);

    WxOpenConfigStorage getWxOpenConfigStorage();

    boolean checkSignature(String timestamp, String nonce, String signature);

    String getComponentAccessToken(boolean forceRefresh) throws WxErrorException;

    String post(String uri, String postData) throws WxErrorException;

    String post(String uri, String postData, String accessTokenKey) throws WxErrorException;

    String get(String uri) throws WxErrorException;

    String get(String uri, String accessTokenKey) throws WxErrorException;

    /**
     * 获取用户授权页URL（来路URL和成功跳转URL 的域名都需要为三方平台设置的 登录授权的发起页域名）.
     */
    String getPreAuthUrl(String redirectUri) throws WxErrorException;

    /**
     * .
     *
     * @param authType 要授权的帐号类型：1则商户点击链接后，手机端仅展示公众号、2表示仅展示小程序，3表示公众号和小程序都展示。如果为未指定，则默认小程序和公众号都展示。第三方平台开发者可以使用本字段来控制授权的帐号类型。
     * @param bizAppid 指定授权唯一的小程序或公众号
     *                 注：authType、bizAppid 互斥。
     */
    String getPreAuthUrl(String redirectUri, String authType, String bizAppid) throws WxErrorException;

    /**
     * 获取预授权链接（手机端预授权）.
     *
     * @param redirectUri .
     * @return .
     * @throws WxErrorException .
     */
    String getMobilePreAuthUrl(String redirectUri) throws WxErrorException;

    /**
     * 获取预授权链接（手机端预授权）.
     *
     * @param redirectUri .
     * @param authType    .
     * @param bizAppid    .
     * @return .
     * @throws WxErrorException .
     */
    String getMobilePreAuthUrl(String redirectUri, String authType, String bizAppid) throws WxErrorException;

    String route(WxOpenXmlMessage wxMessage) throws WxErrorException;

    /**
     * 使用授权码换取公众号或小程序的接口调用凭据和授权信息.
     */
    WxOpenQueryAuthResult getQueryAuth(String authorizationCode) throws WxErrorException;

    /**
     * 获取授权方的帐号基本信息.
     */
    WxOpenAuthorizerInfoResult getAuthorizerInfo(String authorizerAppid) throws WxErrorException;

    /**
     * 获取授权方的选项设置信息.
     */
    WxOpenAuthorizerOptionResult getAuthorizerOption(String authorizerAppid, String optionName) throws WxErrorException;

    /**
     * 获取所有授权方列表.
     */
    WxOpenAuthorizerListResult getAuthorizerList(int begin, int len) throws WxErrorException;

    /**
     * 设置授权方的选项信息.
     */
    void setAuthorizerOption(String authorizerAppid, String optionName, String optionValue) throws WxErrorException;

    String getAuthorizerAccessToken(String appid, boolean forceRefresh) throws WxErrorException;

    WxMpOAuth2AccessToken oauth2getAccessToken(String appid, String code) throws WxErrorException;

    boolean checkSignature(String appId, String timestamp, String nonce, String signature);

    WxMpOAuth2AccessToken oauth2refreshAccessToken(String appid, String refreshToken) throws WxErrorException;

    String oauth2buildAuthorizationUrl(String appid, String redirectUri, String scope, String state);

    WxMaJscode2SessionResult miniappJscode2Session(String appId, String jsCode) throws WxErrorException;

    /**
     * 代小程序实现业务.
     * 小程序代码模版库管理：https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1506504150_nMMh6&token=&lang=zh_CN
     * access_token 为 component_access_token
     */
    String GET_TEMPLATE_DRAFT_LIST_URL = "https://api.weixin.qq.com/wxa/gettemplatedraftlist";
    String GET_TEMPLATE_LIST_URL = "https://api.weixin.qq.com/wxa/gettemplatelist";
    String ADD_TO_TEMPLATE_URL = "https://api.weixin.qq.com/wxa/addtotemplate";
    String DELETE_TEMPLATE_URL = "https://api.weixin.qq.com/wxa/deletetemplate";

    /**
     * 获取草稿箱内的所有临时代码草稿.
     *
     * @return 草稿箱代码模板列表（draftId）
     * @throws WxErrorException 获取失败时返回，具体错误码请看此接口的注释文档
     */
    List<WxOpenMaCodeTemplate> getTemplateDraftList() throws WxErrorException;

    /**
     * 获取代码模版库中的所有小程序代码模版.
     *
     * @return 小程序代码模版列表（templateId）
     * @throws WxErrorException 获取失败时返回，具体错误码请看此接口的注释文档
     */
    List<WxOpenMaCodeTemplate> getTemplateList() throws WxErrorException;

    /**
     * 将草稿箱的草稿选为小程序代码模版.
     *
     * @param draftId 草稿ID，本字段可通过“获取草稿箱内的所有临时代码草稿”接口获得
     * @throws WxErrorException 操作失败时抛出，具体错误码请看此接口的注释文档
     * @see #getTemplateDraftList
     */
    void addToTemplate(long draftId) throws WxErrorException;

    /**
     * 删除指定小程序代码模版.
     *
     * @param templateId 要删除的模版ID
     * @throws WxErrorException 操作失败时抛出，具体错误码请看此接口的注释文档
     * @see #getTemplateList
     */
    void deleteTemplate(long templateId) throws WxErrorException;

    /**
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1498704199_1bcax&token=6df5e3650041eff2cd3ec3662425ad8d7beec8d9&lang=zh_CN
     * 创建 开放平台帐号并绑定公众号/小程序.
     * https://api.weixin.qq.com/cgi-bin/open/create
     *
     * @param appId 公众号/小程序的appId
     * @return .
     */
    WxOpenCreateResult createOpenAccount(String appId) throws WxErrorException;


    /**
     * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/api/account/bind.html
     * 将公众号/小程序绑定到开放平台帐号下
     *
     * @param appId     公众号/小程序的appId
     * @param openAppid 开放平台帐号 appid，由创建开发平台帐号接口返回
     */
    Boolean bindOpenAccount(String appId, String openAppid) throws WxErrorException;


    /**
     * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/api/account/unbind.html
     * 将公众号/小程序从开放平台帐号下解绑
     *
     * @param appId     公众号/小程序的appId
     * @param openAppid 开放平台帐号 appid，由创建开发平台帐号接口返回
     */
    Boolean unbindOpenAccount(String appId, String openAppid) throws WxErrorException;


    /**
     * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/api/account/get.html
     * 获取公众号/小程序所绑定的开放平台帐号
     *
     * @param appId 公众号/小程序的appId
     * @return 开放平台帐号 appid，由创建开发平台帐号接口返回
     */
    WxOpenGetResult getOpenAccount(String appId) throws WxErrorException;

    /**
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=21538208049W8uwq&token=&lang=zh_CN
     * 第三方平台快速创建小程序.
     * 注意：创建任务逻辑串行，单次任务结束后才可以使用相同信息下发第二次任务，请注意规避任务阻塞
     *
     * @param name               企业名（需与工商部门登记信息一致）
     * @param code               企业代码
     * @param codeType           企业代码类型 1：统一社会信用代码（18位） 2：组织机构代码（9位xxxxxxxx-x） 3：营业执照注册号(15位)
     * @param legalPersonaWechat 法人微信号
     * @param legalPersonaName   法人姓名（绑定银行卡）
     * @param componentPhone     第三方联系电话（方便法人与第三方联系）
     * @return .
     * @throws WxErrorException .
     */
    WxOpenResult fastRegisterWeapp(String name, String code, String codeType, String legalPersonaWechat, String legalPersonaName, String componentPhone) throws WxErrorException;

    /**
     * https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=21538208049W8uwq&token=&lang=zh_CN
     * 查询第三方平台快速创建小程序的任务状态
     * 注意：该接口只提供当下任务结果查询，不建议过分依赖该接口查询所创建小程序。
     * 小程序的成功状态可在第三方服务器中自行对账、查询。
     * 不要频繁调用search接口，消息接收需通过服务器查看。调用search接口会消耗接口整体调用quato
     *
     * @param name               企业名（需与工商部门登记信息一致）
     * @param legalPersonaWechat 法人微信号
     * @param legalPersonaName   法人姓名（绑定银行卡）
     * @throws WxErrorException .
     */
    WxOpenResult fastRegisterWeappSearch(String name, String legalPersonaWechat, String legalPersonaName) throws WxErrorException;
}
