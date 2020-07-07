package org.rxjava.third.weixin.common.util.http.apache;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.http.MediaUploadRequestExecutor;
import org.rxjava.third.weixin.common.util.http.RequestHttp;

import java.io.File;
import java.io.IOException;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class ApacheMediaUploadRequestExecutor extends MediaUploadRequestExecutor<CloseableHttpClient, HttpHost> {
    public ApacheMediaUploadRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMediaUploadResult execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }
        if (file != null) {
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("media", file)
                    .setMode(HttpMultipartMode.RFC6532)
                    .build();
            httpPost.setEntity(entity);
        }
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent, wxType);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }
            return WxMediaUploadResult.fromJson(responseContent);
        } finally {
            httpPost.releaseConnection();
        }
    }
}
