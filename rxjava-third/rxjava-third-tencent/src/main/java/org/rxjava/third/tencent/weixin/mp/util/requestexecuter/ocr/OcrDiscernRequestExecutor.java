package org.rxjava.third.tencent.weixin.mp.util.requestexecuter.ocr;

import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.RequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.ResponseHandler;

import java.io.File;
import java.io.IOException;

/**
 */
public abstract class OcrDiscernRequestExecutor<H, P> implements RequestExecutor<String, File> {
    protected RequestHttp<H, P> requestHttp;

    public OcrDiscernRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, File data, ResponseHandler<String> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<String, File> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new OcrDiscernApacheHttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }
}
