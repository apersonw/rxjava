package org.rxjava.third.tencent.weixin.mp.util.requestexecuter.material;

import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.RequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.ResponseHandler;
import org.rxjava.third.tencent.weixin.mp.bean.material.WxMpMaterial;
import org.rxjava.third.tencent.weixin.mp.bean.material.WxMpMaterialUploadResult;

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
