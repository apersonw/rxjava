package top.rxjava.third.weixin.mp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.bean.WxMpUserQuery;
import top.rxjava.third.weixin.mp.bean.result.WxMpChangeOpenid;
import top.rxjava.third.weixin.mp.bean.result.WxMpUser;
import top.rxjava.third.weixin.mp.bean.result.WxMpUserList;

import java.util.List;

/**
 * 用户管理相关操作接口.
 */
public interface WxMpUserService {
    /**
     * 设置用户备注名
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140838&token=&lang=zh_CN
     * http请求方式: POST（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN
     *
     * @param openid 用户openid
     * @param remark 备注名
     */
    void userUpdateRemark(String openid, String remark) throws WxErrorException;

    /**
     * 获取用户基本信息（语言为默认的zh_CN 简体）
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: GET
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param openid 用户openid
     */
    WxMpUser userInfo(String openid) throws WxErrorException;

    /**
     * 获取用户基本信息
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: GET
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN
     *
     * @param openid 用户openid
     * @param lang   语言，zh_CN 简体(默认)，zh_TW 繁体，en 英语
     */
    WxMpUser userInfo(String openid, String lang) throws WxErrorException;

    /**
     * 获取用户基本信息列表
     * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: POST
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
     *
     * @param openidList 用户openid列表
     */
    List<WxMpUser> userInfoList(List<String> openidList) throws WxErrorException;

    /**
     * 获取用户基本信息列表
     * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839&token=&lang=zh_CN
     * http请求方式: POST
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
     *
     * @param userQuery 详细查询参数
     */
    List<WxMpUser> userInfoList(WxMpUserQuery userQuery) throws WxErrorException;

    /**
     * 获取用户列表
     * 公众号可通过本接口来获取帐号的关注者列表，
     * 关注者列表由一串OpenID（加密后的微信号，每个用户对每个公众号的OpenID是唯一的）组成。
     * 一次拉取调用最多拉取10000个关注者的OpenID，可以通过多次拉取的方式来满足需求。
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140840&token=&lang=zh_CN
     * http请求方式: GET（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID
     *
     * @param nextOpenid 可选，第一个拉取的OPENID，null为从头开始拉取
     */
    WxMpUserList userList(String nextOpenid) throws WxErrorException;

    /**
     * 微信公众号主体变更迁移用户 openid
     * 详情请见: http://kf.qq.com/faq/170221aUnmmU170221eUZJNf.html
     * http://kf.qq.com/faq/1901177NrqMr190117nqYJze.html
     * http请求方式: POST
     * 接口地址：https://api.weixin.qq.com/cgi-bin/changeopenid?access_token=ACCESS_TOKEN
     *
     * @param fromAppid  原公众号的 appid
     * @param openidList 需要转换的openid，这些必须是旧账号目前关注的才行，否则会出错；一次最多100个
     */
    List<WxMpChangeOpenid> changeOpenid(String fromAppid, List<String> openidList) throws WxErrorException;
}
