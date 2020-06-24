package org.rxjava.third.tencent.weixin.open.executor;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.tencent.weixin.common.WxType;
import org.rxjava.third.tencent.weixin.common.error.WxError;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.fs.FileUtils;
import org.rxjava.third.tencent.weixin.common.util.http.RequestHttp;
import org.rxjava.third.tencent.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import org.rxjava.third.tencent.weixin.open.bean.ma.WxMaQrcodeParam;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 */
public class MaQrCodeOkhttpRequestExecutor extends MaQrCodeRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    public MaQrCodeOkhttpRequestExecutor(RequestHttp requestHttp) {
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

        OkHttpClient client = requestHttp.getRequestHttpClient();
        Request request = new Request.Builder().url(uri).get().build();
        Response response = client.newCall(request).execute();
        String contentTypeHeader = response.header("Content-Type");
        if ("text/plain".equals(contentTypeHeader)) {
            String responseContent = response.body().string();
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
        }

        try (InputStream inputStream = response.body().byteStream()) {
            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        }

    }
}
