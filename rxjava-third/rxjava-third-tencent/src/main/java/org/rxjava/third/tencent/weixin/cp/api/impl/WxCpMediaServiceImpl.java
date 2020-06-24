package org.rxjava.third.tencent.weixin.cp.api.impl;

import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.common.util.fs.FileUtils;
import org.rxjava.third.tencent.weixin.common.util.http.BaseMediaDownloadRequestExecutor;
import org.rxjava.third.tencent.weixin.common.util.http.MediaUploadRequestExecutor;
import org.rxjava.third.tencent.weixin.cp.api.WxCpMediaService;
import org.rxjava.third.tencent.weixin.cp.api.WxCpService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static org.rxjava.third.tencent.weixin.cp.constant.WxCpApiPathConsts.Media.*;

/**
 * 媒体管理接口.
 */
@RequiredArgsConstructor
public class WxCpMediaServiceImpl implements WxCpMediaService {
    private final WxCpService mainService;

    @Override
    public WxMediaUploadResult upload(String mediaType, String fileType, InputStream inputStream)
            throws WxErrorException, IOException {
        return this.upload(mediaType, FileUtils.createTmpFile(inputStream, UUID.randomUUID().toString(), fileType));
    }

    @Override
    public WxMediaUploadResult upload(String mediaType, File file) throws WxErrorException {
        return this.mainService.execute(MediaUploadRequestExecutor.create(this.mainService.getRequestHttp()),
                this.mainService.getWxCpConfigStorage().getApiUrl(MEDIA_UPLOAD + mediaType), file);
    }

    @Override
    public File download(String mediaId) throws WxErrorException {
        return this.mainService.execute(
                BaseMediaDownloadRequestExecutor.create(this.mainService.getRequestHttp(),
                        this.mainService.getWxCpConfigStorage().getTmpDirFile()),
                this.mainService.getWxCpConfigStorage().getApiUrl(MEDIA_GET), "media_id=" + mediaId);
    }

    @Override
    public File getJssdkFile(String mediaId) throws WxErrorException {
        return this.mainService.execute(
                BaseMediaDownloadRequestExecutor.create(this.mainService.getRequestHttp(),
                        this.mainService.getWxCpConfigStorage().getTmpDirFile()),
                this.mainService.getWxCpConfigStorage().getApiUrl(JSSDK_MEDIA_GET), "media_id=" + mediaId);
    }

    @Override
    public String uploadImg(File file) throws WxErrorException {
        final String url = this.mainService.getWxCpConfigStorage().getApiUrl(IMG_UPLOAD);
        return this.mainService.execute(MediaUploadRequestExecutor.create(this.mainService.getRequestHttp()), url, file)
                .getUrl();
    }
}
