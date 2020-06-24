package org.rxjava.third.tencent.weixin.common.util.http.okhttp;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSink;
import okio.Okio;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.http.BaseMediaDownloadRequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.HttpResponseProxy;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;

import java.io.File;
import java.io.IOException;

/**
 */
@Slf4j
public class OkHttpMediaDownloadRequestExecutor extends BaseMediaDownloadRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public OkHttpMediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
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

        //得到httpClient
        OkHttpClient client = requestHttp.getRequestHttpClient();

        Request request = new Request.Builder().url(uri).get().build();

        Response response = client.newCall(request).execute();

        String contentType = response.header("Content-Type");
        if (contentType != null && contentType.startsWith("application/json")) {
            // application/json; encoding=utf-8 下载媒体文件出错
            throw new WxErrorException(WxError.fromJson(response.body().string(), wxType));
        }

        String fileName = new HttpResponseProxy(response).getFileName();
        if (StringUtils.isBlank(fileName)) {
            return null;
        }

        String baseName = FilenameUtils.getBaseName(fileName);
        if (StringUtils.isBlank(fileName) || baseName.length() < 3) {
            baseName = String.valueOf(System.currentTimeMillis());
        }

        File file = File.createTempFile(
                baseName, "." + FilenameUtils.getExtension(fileName), super.tmpDirFile
        );

        try (BufferedSink sink = Okio.buffer(Okio.sink(file))) {
            sink.writeAll(response.body().source());
        }

        file.deleteOnExit();
        return file;
    }

}
