package org.rxjava.third.weixin.mp.util.requestexecuter.material;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.RequestHttp;
import org.rxjava.third.weixin.mp.bean.material.WxMpMaterialVideoInfoResult;

import java.io.IOException;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class MaterialVideoInfoJoddHttpRequestExecutor extends MaterialVideoInfoRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public MaterialVideoInfoJoddHttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialVideoInfoResult execute(String uri, String materialId, WxType wxType) throws WxErrorException, IOException {
        HttpRequest request = HttpRequest.post(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
        }
        request.withConnectionProvider(requestHttp.getRequestHttpClient());

        request.query("media_id", materialId);
        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);
        String responseContent = response.bodyText();
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpMaterialVideoInfoResult.fromJson(responseContent);
        }
    }
}