package org.rxjava.third.tencent.common.util.http;

import java.io.File;
import java.io.IOException;

import org.rxjava.third.tencent.common.WxType;
import org.rxjava.third.tencent.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.common.error.WxErrorException;
import org.rxjava.third.tencent.common.util.http.apache.ApacheMediaUploadRequestExecutor;
import org.rxjava.third.tencent.common.util.http.jodd.JoddHttpMediaUploadRequestExecutor;
import org.rxjava.third.tencent.common.util.http.okhttp.OkHttpMediaUploadRequestExecutor;

/**
 * 上传媒体文件请求执行器.
 * 请求的参数是File, 返回的结果是String
 *
 * @author Daniel Qian
 */
public abstract class MediaUploadRequestExecutor<H, P> implements RequestExecutor<WxMediaUploadResult, File> {
  protected RequestHttp<H, P> requestHttp;

  public MediaUploadRequestExecutor(RequestHttp requestHttp) {
    this.requestHttp = requestHttp;
  }

  @Override
  public void execute(String uri, File data, ResponseHandler<WxMediaUploadResult> handler, WxType wxType) throws WxErrorException, IOException {
    handler.handle(this.execute(uri, data, wxType));
  }

  public static RequestExecutor<WxMediaUploadResult, File> create(RequestHttp requestHttp) {
    switch (requestHttp.getRequestType()) {
      case APACHE_HTTP:
        return new ApacheMediaUploadRequestExecutor(requestHttp);
      case JODD_HTTP:
        return new JoddHttpMediaUploadRequestExecutor(requestHttp);
      case OK_HTTP:
        return new OkHttpMediaUploadRequestExecutor(requestHttp);
      default:
        return null;
    }
  }

}
