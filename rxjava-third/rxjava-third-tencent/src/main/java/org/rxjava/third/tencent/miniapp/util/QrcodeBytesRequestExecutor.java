package org.rxjava.third.tencent.miniapp.util;

import org.rxjava.third.tencent.miniapp.bean.AbstractWxMaQrcodeWrapper;
import org.rxjava.third.tencent.common.WxType;
import org.rxjava.third.tencent.common.error.WxError;
import org.rxjava.third.tencent.common.error.WxErrorException;
import org.rxjava.third.tencent.common.util.http.RequestExecutor;
import org.rxjava.third.tencent.common.util.http.RequestHttp;
import org.rxjava.third.tencent.common.util.http.ResponseHandler;
import org.rxjava.third.tencent.common.util.http.apache.InputStreamResponseHandler;
import org.rxjava.third.tencent.common.util.http.apache.Utf8ResponseHandler;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public class QrcodeBytesRequestExecutor implements RequestExecutor<byte[], AbstractWxMaQrcodeWrapper> {
  protected RequestHttp<CloseableHttpClient, HttpHost> requestHttp;

  public QrcodeBytesRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, AbstractWxMaQrcodeWrapper data, ResponseHandler<byte[]> handler, WxType wxType) throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  @Override
  public byte[] execute(String uri, AbstractWxMaQrcodeWrapper qrcodeWrapper, WxType wxType) throws WxErrorException, IOException {
    HttpPost httpPost = new HttpPost(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      httpPost.setConfig(
        RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build()
      );
    }

    httpPost.setEntity(new StringEntity(qrcodeWrapper.toJson()));

    try (final CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost);
         final InputStream inputStream = InputStreamResponseHandler.INSTANCE.handleResponse(response)) {
      Header[] contentTypeHeader = response.getHeaders("Content-Type");
      if (contentTypeHeader != null && contentTypeHeader.length > 0
        && ContentType.APPLICATION_JSON.getMimeType()
        .equals(ContentType.parse(contentTypeHeader[0].getValue()).getMimeType())) {
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
        throw new WxErrorException(WxError.fromJson(responseContent, wxType));
      }

      return IOUtils.toByteArray(inputStream);
    } finally {
      httpPost.releaseConnection();
    }
  }
}
