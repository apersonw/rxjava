package top.rxjava.third.weixin.mp.util.requestexecuter.qrcode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.fs.FileUtils;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.okhttp.OkHttpProxyInfo;
import top.rxjava.third.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 *
 */
public class QrCodeOkhttpRequestExecutor extends QrCodeRequestExecutor<OkHttpClient, OkHttpProxyInfo> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public QrCodeOkhttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public File execute(String uri, WxMpQrCodeTicket ticket, WxType wxType) throws WxErrorException, IOException {
        logger.debug("QrCodeOkhttpRequestExecutor is running");

        if (ticket != null) {
            if (uri.indexOf('?') == -1) {
                uri += '?';
            }
            uri += uri.endsWith("?")
                    ? "ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8")
                    : "&ticket=" + URLEncoder.encode(ticket.getTicket(), "UTF-8");
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
