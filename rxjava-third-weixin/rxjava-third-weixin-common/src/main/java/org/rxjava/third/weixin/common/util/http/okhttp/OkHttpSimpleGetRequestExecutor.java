package top.rxjava.third.weixin.common.util.http.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.SimpleGetRequestExecutor;

import java.io.IOException;

/**
 */
public class OkHttpSimpleGetRequestExecutor extends SimpleGetRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public OkHttpSimpleGetRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, String queryParam, WxType wxType) throws WxErrorException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        //得到httpClient
        OkHttpClient client = requestHttp.getRequestHttpClient();
        Request request = new Request.Builder().url(uri).build();
        Response response = client.newCall(request).execute();
        return this.handleResponse(wxType, response.body().string());
    }

}
