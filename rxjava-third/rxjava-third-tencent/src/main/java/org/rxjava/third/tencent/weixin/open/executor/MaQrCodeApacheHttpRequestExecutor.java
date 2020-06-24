package org.rxjava.third.tencent.weixin.open.executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.fs.FileUtils;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.apache.InputStreamResponseHandler;
import org.rxjava.third.tencent.weixin.common.util.http.apache.Utf8ResponseHandler;
import org.rxjava.third.tencent.weixin.open.bean.ma.WxMaQrcodeParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 */
public class MaQrCodeApacheHttpRequestExecutor extends MaQrCodeRequestExecutor<CloseableHttpClient, HttpHost> {
    public MaQrCodeApacheHttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public File execute(String uri, WxMaQrcodeParam qrcodeParam, WxType wxType) throws WxErrorException, IOException {
        if (qrcodeParam != null && StringUtils.isNotBlank(qrcodeParam.getPagePath())) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?")
                    ? "path=" + URLEncoder.encode(qrcodeParam.getRequestPath(), "UTF-8")
                    : "&path=" + URLEncoder.encode(qrcodeParam.getRequestPath(), "UTF-8");
        }

        HttpGet httpGet = new HttpGet(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpGet.setConfig(config);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet);
             InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response);) {
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            if (contentTypeHeader != null && contentTypeHeader.length > 0) {
                // 出错
                if (ContentType.TEXT_PLAIN.getMimeType()
                        .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
                    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                    throw new WxErrorException(WxError.fromJson(responseContent, WxType.MiniApp));
                }
            }
            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        } finally {
            httpGet.releaseConnection();
        }
    }
}
