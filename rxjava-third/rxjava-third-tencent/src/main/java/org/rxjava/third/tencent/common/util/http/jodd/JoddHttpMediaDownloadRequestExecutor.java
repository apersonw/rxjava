package org.rxjava.third.tencent.common.util.http.jodd;

import jodd.http.HttpConnectionProvider;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;
import jodd.http.ProxyInfo;
import jodd.util.StringPool;
import org.rxjava.third.tencent.common.WxType;
import org.rxjava.third.tencent.common.error.WxError;
import org.rxjava.third.tencent.common.error.WxErrorException;
import org.rxjava.third.tencent.common.util.fs.FileUtils;
import org.rxjava.third.tencent.common.util.http.BaseMediaDownloadRequestExecutor;
import org.rxjava.third.tencent.common.util.http.HttpResponseProxy;
import org.rxjava.third.tencent.common.util.http.RequestHttp;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * .
 *
 * @author ecoolper
 * @date 2017/5/5
 */
public class JoddHttpMediaDownloadRequestExecutor extends BaseMediaDownloadRequestExecutor<HttpConnectionProvider, ProxyInfo> {
  public JoddHttpMediaDownloadRequestExecutor(RequestHttp requestHttp, File tmpDirFile) {
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

    HttpRequest request = HttpRequest.get(uri);
    if (requestHttp.getRequestHttpProxy() != null) {
      requestHttp.getRequestHttpClient().useProxy(requestHttp.getRequestHttpProxy());
    }
    request.withConnectionProvider(requestHttp.getRequestHttpClient());

    HttpResponse response = request.send();
    response.charset(StringPool.UTF_8);

    String contentType = response.header("Content-Type");
    if (contentType != null && contentType.startsWith("application/json")) {
      // application/json; encoding=utf-8 下载媒体文件出错
      throw new WxErrorException(WxError.fromJson(response.bodyText(), wxType));
    }

    String fileName = new HttpResponseProxy(response).getFileName();
    if (StringUtils.isBlank(fileName)) {
      return null;
    }

    String baseName = FilenameUtils.getBaseName(fileName);
    if (StringUtils.isBlank(fileName) || baseName.length() < 3) {
      baseName = String.valueOf(System.currentTimeMillis());
    }

    try (InputStream inputStream = new ByteArrayInputStream(response.bodyBytes())) {
      return FileUtils.createTmpFile(inputStream,
        baseName,
        FilenameUtils.getExtension(fileName),
        super.tmpDirFile);
    }
  }


}
