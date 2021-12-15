package top.rxjava.third.weixin.mp.util.requestexecuter.material;

import com.google.common.collect.ImmutableMap;
import okhttp3.*;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import top.rxjava.third.weixin.common.util.json.WxGsonBuilder;
import top.rxjava.third.weixin.mp.bean.material.WxMpMaterialVideoInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class MaterialVideoInfoOkhttpRequestExecutor extends MaterialVideoInfoRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MaterialVideoInfoOkhttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialVideoInfoResult execute(String uri, String materialId, WxType wxType) throws WxErrorException, IOException {
        logger.debug("MaterialVideoInfoOkhttpRequestExecutor is running");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"),
                WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId)));
        Request request = new Request.Builder().url(uri).post(requestBody).build();
        Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
        String responseContent = response.body().string();
        WxError error = WxError.fromJson(responseContent, WxType.MP);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        } else {
            return WxMpMaterialVideoInfoResult.fromJson(responseContent);
        }
    }
}
