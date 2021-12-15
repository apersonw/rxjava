package top.rxjava.third.weixin.cp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.cp.bean.WxCpAgent;

import java.util.List;

/**
 * 管理企业号应用
 * 文档地址：https://work.weixin.qq.com/api/doc#10087
 */
public interface WxCpAgentService {
    /**
     * 获取企业号应用信息
     * 该API用于获取企业号某个应用的基本信息，包括头像、昵称、帐号类型、认证类型、可见范围等信息
     * 详情请见: https://work.weixin.qq.com/api/doc#10087
     *
     * @param agentId 企业应用的id
     * @return 部门id
     */
    WxCpAgent get(Integer agentId) throws WxErrorException;

    /**
     * 设置应用.
     * 仅企业可调用，可设置当前凭证对应的应用；第三方不可调用。
     * 详情请见: https://work.weixin.qq.com/api/doc#10088
     *
     * @param agentInfo 应用信息
     */
    void set(WxCpAgent agentInfo) throws WxErrorException;

    /**
     * 获取应用列表.
     * 企业仅可获取当前凭证对应的应用；第三方仅可获取被授权的应用。
     * 详情请见: https://work.weixin.qq.com/api/doc#11214
     */
    List<WxCpAgent> list() throws WxErrorException;

}
