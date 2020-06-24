package org.rxjava.third.tencent.weixin.common.util.http.okhttp;

import okhttp3.*;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.MediaUploadRequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;

import java.io.File;
import java.io.IOException;

/**
 */
public class OkHttpMediaUploadRequestExecutor extends MediaUploadRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public OkHttpMediaUploadRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMediaUploadResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {

        RequestBody body = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("media",
                        file.getName(),
                        RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder().url(uri).post(body).build();

        Response response = requestHttp.getRequestHttpClient().newCall(request).execute();
        String responseContent = response.body().string();
        WxError error = WxError.fromJson(responseContent, wxType);
        if (error.getErrorCode() != 0) {
            throw new WxErrorException(error);
        }
        return WxMediaUploadResult.fromJson(responseContent);
    }

}
