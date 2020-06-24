package org.rxjava.third.tencent.weixin.common.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.SimplePostRequestExecutor;

import java.io.IOException;

/**
 */
public class JoddHttpSimplePostRequestExecutor extends SimplePostRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public JoddHttpSimplePostRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, String postEntity, WxType wxType) throws WxErrorException, IOException {
        HttpConnectionProvider provider = requestHttp.getRequestHttpClient();
        ProxyInfo proxyInfo = requestHttp.getRequestHttpProxy();

        HttpRequest request = HttpRequest.post(uri);
        if (proxyInfo != null) {
            provider.useProxy(proxyInfo);
        }
        request.withConnectionProvider(provider);
        if (postEntity != null) {
            request.bodyText(postEntity);
        }
        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);

        return this.handleResponse(wxType, response.bodyText());
    }

}
