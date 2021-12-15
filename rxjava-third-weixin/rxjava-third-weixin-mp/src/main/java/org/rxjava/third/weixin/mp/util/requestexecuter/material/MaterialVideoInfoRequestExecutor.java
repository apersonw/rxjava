package top.rxjava.third.weixin.mp.util.requestexecuter.material;


import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestExecutor;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.ResponseHandler;
import top.rxjava.third.weixin.mp.bean.material.WxMpMaterialVideoInfoResult;

import java.io.IOException;

public abstract class MaterialVideoInfoRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialVideoInfoResult, String> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialVideoInfoRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<WxMpMaterialVideoInfoResult> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<WxMpMaterialVideoInfoResult, String> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialVideoInfoApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialVideoInfoJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialVideoInfoOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
