package org.rxjava.third.tencent.miniapp.api.impl;

import org.rxjava.third.tencent.miniapp.api.WxMaSecCheckService;
import org.rxjava.third.tencent.miniapp.api.WxMaService;
import org.rxjava.third.tencent.miniapp.bean.WxMaMediaAsyncCheckResult;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.common.error.WxError;
import org.rxjava.third.tencent.common.error.WxErrorException;
import org.rxjava.third.tencent.common.util.http.MediaUploadRequestExecutor;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * <pre>
 *
 * Created by Binary Wang on 2018/11/24.
 * </pre>
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@AllArgsConstructor
public class WxMaSecCheckServiceImpl implements WxMaSecCheckService {
  private WxMaService service;

  @Override
  public boolean checkImage(File file) throws WxErrorException {
    WxMediaUploadResult result = this.service.execute(MediaUploadRequestExecutor
      .create(this.service.getRequestHttp()), IMG_SEC_CHECK_URL, file);
    return result != null;
  }

  @Override
  public boolean checkImage(String fileUrl) throws WxErrorException {
    File file = new File(FileUtils.getTempDirectory(), System.currentTimeMillis() + ".tmp");
    try {
      URL url = new URL(fileUrl);
      FileUtils.copyURLToFile(url, file);
    } catch (IOException e) {
      throw new WxErrorException(WxError.builder().errorCode(-1).errorMsg("文件地址读取异常").build(), e);
    }
    
    return this.checkImage(file);
  }

  @Override
  public boolean checkMessage(String msgString) throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("content", msgString);

    this.service.post(MSG_SEC_CHECK_URL, jsonObject.toString());

    return true;
  }

  @Override
  public WxMaMediaAsyncCheckResult mediaCheckAsync(String mediaUrl, int mediaType)
    throws WxErrorException {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("media_url", mediaUrl);
    jsonObject.addProperty("media_type", mediaType);

    return WxMaMediaAsyncCheckResult
      .fromJson(this.service.post(MEDIA_CHECK_ASYNC_URL, jsonObject.toString()));
  }

}
