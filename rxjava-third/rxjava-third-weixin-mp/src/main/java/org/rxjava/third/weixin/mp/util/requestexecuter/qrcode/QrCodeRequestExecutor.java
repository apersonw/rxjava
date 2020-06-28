package org.rxjava.third.weixin.mp.util.requestexecuter.qrcode;

import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.RequestExecutor;
import org.rxjava.third.weixin.common.util.http.RequestHttp;
import org.rxjava.third.weixin.common.util.http.ResponseHandler;
import org.rxjava.third.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.io.File;
import java.io.IOException;

/**
 * 获得QrCode图片 请求执行器.
 */
public abstract class QrCodeRequestExecutor<H, P> implements RequestExecutor<File, WxMpQrCodeTicket> {
    protected RequestHttp<H, P> requestHttp;

    public QrCodeRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, WxMpQrCodeTicket data, ResponseHandler<File> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<File, WxMpQrCodeTicket> create(RequestHttp requestHttp) throws WxErrorException {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new QrCodeApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new QrCodeJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new QrCodeOkhttpRequestExecutor(requestHttp);
            default:
                throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("不支持的http框架").build());
        }
    }

}
