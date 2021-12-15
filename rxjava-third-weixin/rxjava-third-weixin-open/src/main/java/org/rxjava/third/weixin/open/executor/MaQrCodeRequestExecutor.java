package top.rxjava.third.weixin.open.executor;

import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestExecutor;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.ResponseHandler;
import top.rxjava.third.weixin.open.bean.ma.WxMaQrcodeParam;

import java.io.File;
import java.io.IOException;

/**
 * 获得小程序体验QrCode图片 请求执行器.
 */
public abstract class MaQrCodeRequestExecutor<H, P> implements RequestExecutor<File, WxMaQrcodeParam> {
    protected RequestHttp<H, P> requestHttp;

    public MaQrCodeRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, WxMaQrcodeParam data, ResponseHandler<File> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<File, WxMaQrcodeParam> create(RequestHttp requestHttp) throws WxErrorException {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaQrCodeApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaQrCodeJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaQrCodeOkhttpRequestExecutor(requestHttp);
            default:
                throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("不支持的http框架").build());
        }
    }

}
