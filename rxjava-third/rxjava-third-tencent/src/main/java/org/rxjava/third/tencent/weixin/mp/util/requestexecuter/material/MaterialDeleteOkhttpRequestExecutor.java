package org.rxjava.third.tencent.weixin.mp.util.requestexecuter.material;

import com.google.common.collect.ImmutableMap;
import okhttp3.*;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.ResponseHandler;
import org.rxjava.third.tencent.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import org.rxjava.third.tencent.weixin.common.util.json.WxGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * .
 *
 * @author ecoolper
 * @date 2017/5/5
 */
public class MaterialDeleteOkhttpRequestExecutor extends MaterialDeleteRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MaterialDeleteOkhttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public void execute(String uri, String data, ResponseHandler<Boolean> handler, WxType wxType)
            throws WxErrorException, IOException {
        handler.handle(this.execute(uri, data, wxType));
    }

    @Override
    public Boolean execute(String uri, String materialId, WxType wxType) throws WxErrorException, IOException {
        logger.debug("MaterialDeleteOkhttpRequestExecutor is running");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId)));
        Request request = new Request.Builder().url(uri).post(requestBody).build();
        Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
        String responseContent = response.body().string();
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }

        return true;
    }
}
