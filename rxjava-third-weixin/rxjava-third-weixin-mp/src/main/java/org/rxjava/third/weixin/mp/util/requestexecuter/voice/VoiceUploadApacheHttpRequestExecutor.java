package top.rxjava.third.weixin.mp.util.requestexecuter.voice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.apache.Utf8ResponseHandler;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class VoiceUploadApacheHttpRequestExecutor extends VoiceUploadRequestExecutor<CloseableHttpClient, HttpHost> {
    public VoiceUploadApacheHttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public Boolean execute(String uri, File data, WxType wxType) throws WxErrorException, IOException {
        if (data == null) {
            throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("文件对象为空").build());
        }

        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        HttpEntity entity = MultipartEntityBuilder
                .create()
                .addBinaryBody("media", data)
                .setMode(HttpMultipartMode.RFC6532)
                .build();
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", ContentType.MULTIPART_FORM_DATA.toString());

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            }

            return true;
        } finally {
            httpPost.releaseConnection();
        }
    }
}
