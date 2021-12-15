package top.rxjava.third.weixin.mp.util.requestexecuter.qrcode;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.fs.FileUtils;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.apache.InputStreamResponseHandler;
import top.rxjava.third.weixin.common.util.http.apache.Utf8ResponseHandler;
import top.rxjava.third.weixin.mp.bean.result.WxMpQrCodeTicket;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by ecoolper on 2017/5/5.
 */
public class QrCodeApacheHttpRequestExecutor extends QrCodeRequestExecutor<CloseableHttpClient, HttpHost> {
    public QrCodeApacheHttpRequestExecutor(RequestHttp requestHttp) {
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
                    throw new WxErrorException(WxError.fromJson(responseContent, WxType.MP));
                }
            }
            return FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), "jpg");
        } finally {
            httpGet.releaseConnection();
        }
    }
}
