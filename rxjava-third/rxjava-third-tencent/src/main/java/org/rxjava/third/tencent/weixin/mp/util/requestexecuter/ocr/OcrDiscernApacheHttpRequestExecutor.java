package org.rxjava.third.tencent.weixin.mp.util.requestexecuter.ocr;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.apache.Utf8ResponseHandler;

import java.io.File;
import java.io.IOException;

/**
 * .
 *
 * @author : zhayueran
 * @date 2019/6/27 14:06
 */
public class OcrDiscernApacheHttpRequestExecutor extends OcrDiscernRequestExecutor<CloseableHttpClient, HttpHost> {
    public OcrDiscernApacheHttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public String execute(String uri, File file, WxType wxType) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }
        if (file != null) {
            HttpEntity entity = MultipartEntityBuilder
                    .create()
                    .addBinaryBody("file", file)
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
            return responseContent;
        } finally {
            httpPost.releaseConnection();
        }
    }
}
