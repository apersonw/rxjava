package top.rxjava.third.weixin.mp.util.requestexecuter.qrcode;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.net.MimeTypes;
import jodd.util.StringPool;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.fs.FileUtils;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class QrCodeJoddHttpRequestExecutor extends QrCodeRequestExecutor<HttpConnectionProvider, ProxyInfo> {
    public QrCodeJoddHttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public File execute(String uri, WxMpQrCodeTicket ticket, WxType wxType) throws WxErrorException, IOException {
        if (ticket != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?")
                    ? "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8")
                    : "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
        }

        HttpRequest request = HttpRequest.get(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
        }
        request.withConnectionProvider(requestHttp.getRequestHttpClient());

        HttpResponse response = request.send();
        response.charset(StringPool.UTF_8);
        String contentTypeHeader = response.header("Content-Type");
        if (MimeTypes.MIME_TEXT_PLAIN.equals(contentTypeHeader)) {
            String responseContent = response.bodyText();
            throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
        }
        try (InputStream inputStream = new ByteArrayInputStream(response.bodyBytes())) {
            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        }
    }
}
