package org.rxjava.third.weixin.mp.util.requestexecuter.material;

import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.RequestExecutor;
import org.rxjava.third.weixin.common.util.http.RequestHttp;
import org.rxjava.third.weixin.common.util.http.ResponseHandler;

import java.io.IOException;

public abstract class MaterialDeleteRequestExecutor<H, P> implements RequestExecutor<Boolean, String> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialDeleteRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<Boolean> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<Boolean, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialDeleteApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialDeleteJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialDeleteOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
