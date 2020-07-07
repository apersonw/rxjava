package org.rxjava.third.weixin.cp.api.impl;

import lombok.RequiredArgsConstructor;
import org.rxjava.third.weixin.common.bean.WxAccessToken;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.cp.api.WxCpTpService;

/**
 * 默认接口实现类，使用apache httpclient实现，配合第三方应用service使用
 */
@RequiredArgsConstructor
public class WxCpServiceOnTpImpl extends WxCpServiceApacheHttpClientImpl {
    private final WxCpTpService wxCpTpService;

    @Override
    public String getAccessToken(boolean forceRefresh) throws WxErrorException {
        if (!this.configStorage.isAccessTokenExpired() && !forceRefresh) {
            return this.configStorage.getAccessToken();
        }
        //access token通过第三方应用service获取
        //corpSecret对应企业永久授权码
        WxAccessToken accessToken = wxCpTpService.getCorpToken(this.configStorage.getCorpId(), this.configStorage.getCorpSecret());

        this.configStorage.updateAccessToken(accessToken.getAccessToken(), accessToken.getExpiresIn());
        return this.configStorage.getAccessToken();
    }

}
