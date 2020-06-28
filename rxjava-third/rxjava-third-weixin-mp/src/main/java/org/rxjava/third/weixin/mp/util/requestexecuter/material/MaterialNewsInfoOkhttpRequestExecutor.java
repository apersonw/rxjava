package org.rxjava.third.weixin.mp.util.requestexecuter.material;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.RequestHttp;
import org.rxjava.third.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import org.rxjava.third.weixin.common.util.json.WxGsonBuilder;
import org.rxjava.third.weixin.mp.bean.material.WxMpMaterialNews;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.IOException;

/**
 */
@Slf4j
public class MaterialNewsInfoOkhttpRequestExecutor extends MaterialNewsInfoRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public MaterialNewsInfoOkhttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialNews execute(String uri, String materialId, WxType wxType) throws WxErrorException, IOException {
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId)));
        Request request = new Request.Builder().url(uri).post(requestBody).build();

        Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
        String responseContent = response.body().string();
        log.debug("响应原始数据：{}", responseContent);

        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpGsonBuilder.create().fromJson(responseContent, WxMpMaterialNews.class);
        }
    }
}
