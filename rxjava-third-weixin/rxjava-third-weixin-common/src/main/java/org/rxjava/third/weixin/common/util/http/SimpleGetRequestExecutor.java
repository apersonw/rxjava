package top.rxjava.third.weixin.common.util.http;

import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.apache.ApacheSimpleGetRequestExecutor;
import top.rxjava.third.weixin.common.util.http.jodd.JoddHttpSimpleGetRequestExecutor;
import top.rxjava.third.weixin.common.util.http.okhttp.OkHttpSimpleGetRequestExecutor;

import java.io.IOException;

/**
 * 简单的GET请求执行器.
 * 请求的参数是String, 返回的结果也是String
 */
public abstract class SimpleGetRequestExecutor<H, P> implements RequestExecutor<String, String> {
    protected RequestHttp<H, P> requestHttp;

    public SimpleGetRequestExecutor(RequestHttp<H, P> requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<String> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<String, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new ApacheSimpleGetRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new JoddHttpSimpleGetRequestExecutor(requestHttp);
            case OK_HTTP:
                return new OkHttpSimpleGetRequestExecutor(requestHttp);
            default:
                throw new IllegalArgumentException("非法请求参数");
        }
    }

    protected String handleResponse(WxType wxType, String responseContent) throws WxErrorException {
        WxError error = WxError.fromJson(responseContent, wxType);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }

        return responseContent;
    }
}
