package top.rxjava.third.weixin.mp.util.requestexecuter.material;

import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestExecutor;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.ResponseHandler;
import top.rxjava.third.weixin.mp.bean.material.WxMpMaterial;
import top.rxjava.third.weixin.mp.bean.material.WxMpMaterialUploadResult;

import java.io.IOException;

/**
 */
public abstract class MaterialUploadRequestExecutor<H, P> implements RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> {
    protected RequestHttp<H, P> requestHttp;

    public MaterialUploadRequestExecutor(RequestHttp requestHttp) {
        this.requestHttp = requestHttp;
    }

    @Override
    public void execute(String uri, WxMpMaterial data, ResponseHandler<WxMpMaterialUploadResult> handler, WxType wxType) throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    public static RequestExecutor<WxMpMaterialUploadResult, WxMpMaterial> create(RequestHttp requestHttp) {
        switch (requestHttp.getRequestType()) {
            case APACHE_HTTP:
                return new MaterialUploadApacheHttpRequestExecutor(requestHttp);
            case JODD_HTTP:
                return new MaterialUploadJoddHttpRequestExecutor(requestHttp);
            case OK_HTTP:
                return new MaterialUploadOkhttpRequestExecutor(requestHttp);
            default:
                return null;
        }
    }

}
