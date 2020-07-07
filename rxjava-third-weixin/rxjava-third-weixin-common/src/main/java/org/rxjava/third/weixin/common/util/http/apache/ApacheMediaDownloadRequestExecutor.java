package org.rxjava.third.weixin.common.util.http.apache;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.rxjava.third.weixin.common.WxType;
import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.common.util.fs.FileUtils;
import org.rxjava.third.weixin.common.util.http.BaseMediaDownloadRequestExecutor;
import org.rxjava.third.weixin.common.util.http.HttpResponseProxy;
import org.rxjava.third.weixin.common.util.http.RequestHttp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 */
public class ApacheMediaDownloadRequestExecutor extends BaseMediaDownloadRequestExecutor<CloseableHttpClient, HttpHost> {
    public ApacheMediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
        super(requestHttp, tmpDirFile);
    }

    @Override
    public File execute(String uri, String queryParam, WxType wxType) throws WxErrorException, IOException {
        if (queryParam != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?") ? queryParam : '&' + queryParam;
        }

        HttpGet httpGet = new HttpGet(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpGet.setConfig(config);
        }

        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpGet);
             InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
            Header[] contentTypeHeader = response.getHeaders("Content-Type");
            if (contentTypeHeader != null && contentTypeHeader.length > 0) {
                if (contentTypeHeader[0].getValue().startsWith(ContentType.APPLICATION_JSON.getMimeType())) {
                    // application/json; encoding=utf-8 下载媒体文件出错
                    String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
                    throw new WxErrorException(WxError.fromJson(responseContent, wxType));
                }
            }

            String fileName = new HttpResponseProxy(response).getFileName();
            if (StringUtils.isBlank(fileName)) {
                fileName = String.valueOf(System.currentTimeMillis());
            }

            String baseName = FilenameUtils.getBaseName(fileName);
            if (StringUtils.isBlank(fileName) || baseName.length() < 3) {
                baseName = String.valueOf(System.currentTimeMillis());
            }

            return FileUtils.createTmpFile(inputStream, baseName, FilenameUtils.getExtension(fileName),
                    super.tmpDirFile);

        } finally {
            httpGet.releaseConnection();
        }
    }

}
