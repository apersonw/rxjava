package org.rxjava.third.tencent.miniapp.api.impl;

import org.rxjava.third.tencent.miniapp.api.WxMaMediaService;
import org.rxjava.third.tencent.miniapp.api.WxMaService;
import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.common.error.WxError;
import org.rxjava.third.tencent.common.error.WxErrorException;
import org.rxjava.third.tencent.common.util.fs.FileUtils;
import org.rxjava.third.tencent.common.util.http.BaseMediaDownloadRequestExecutor;
import org.rxjava.third.tencent.common.util.http.MediaUploadRequestExecutor;
import org.rxjava.third.tencent.common.util.http.RequestExecutor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@AllArgsConstructor
public class WxMaMediaServiceImpl implements WxMaMediaService {
  private WxMaService wxMaService;

  @Override
  public WxMediaUploadResult uploadMedia(String mediaType, String fileType, InputStream inputStream) throws WxErrorException {
    try {
      return this.uploadMedia(mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    } catch (IOException e) {
      throw new WxErrorException(WxError.builder().errorMsg(e.getMessage()).build(), e);
    }
  }

  @Override
  public WxMediaUploadResult uploadMedia(String mediaType, File file) throws WxErrorException {
    String url = String.format(MEDIA_UPLOAD_URL, mediaType);
    return this.wxMaService.execute(MediaUploadRequestExecutor.create(this.wxMaService.getRequestHttp()), url, file);
  }

  @Override
  public File getMedia(String mediaId) throws WxErrorException {
    try {
      RequestExecutor<File, String> executor = BaseMediaDownloadRequestExecutor
        .create(this.wxMaService.getRequestHttp(), Files.createTempDirectory("wxma").toFile());
      return this.wxMaService.execute(executor, MEDIA_GET_URL, "media_id=" + mediaId);
    } catch (IOException e) {
      throw new WxErrorException(WxError.builder().errorMsg(e.getMessage()).build(), e);
    }
  }

}
