package org.rxjava.third.tencent.weixin.mp.api;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.bean.result.WxMpUserBlacklistGetResult;

import java.util.List;

/**
 * @author miller
 */
public interface WxMpUserBlacklistService {
    /**
     * 获取公众号的黑名单列表
     * 详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
     */
    WxMpUserBlacklistGetResult getBlacklist(String nextOpenid) throws WxErrorException;

    /**
     * 拉黑用户
     * 详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
     */
    void pushToBlacklist(List<String> openidList) throws WxErrorException;

    /**
     * 取消拉黑用户
     * 详情请见http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1471422259_pJMWA&token=&lang=zh_CN
     */
    void pullFromBlacklist(List<String> openidList) throws WxErrorException;
}
