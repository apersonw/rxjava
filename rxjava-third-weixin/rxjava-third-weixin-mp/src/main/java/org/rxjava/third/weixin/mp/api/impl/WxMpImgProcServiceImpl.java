package top.rxjava.third.weixin.mp.api.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.api.WxMpImgProcService;
import top.rxjava.third.weixin.mp.api.WxMpService;
import top.rxjava.third.weixin.mp.bean.imgproc.WxMpImgProcAiCropResult;
import top.rxjava.third.weixin.mp.bean.imgproc.WxMpImgProcQrCodeResult;
import top.rxjava.third.weixin.mp.bean.imgproc.WxMpImgProcSuperResolutionResult;
import top.rxjava.third.weixin.mp.util.requestexecuter.ocr.OcrDiscernRequestExecutor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static top.rxjava.third.weixin.mp.enums.WxMpApiUrl.ImgProc.*;

/**
 * 图像处理接口实现.
 */
@RequiredArgsConstructor
public class WxMpImgProcServiceImpl implements WxMpImgProcService {
    private final WxMpService wxMpService;

    @Override
    public WxMpImgProcQrCodeResult qrCode(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            //ignore
        }

        final String result = this.wxMpService.get(String.format(QRCODE.getUrl(this.wxMpService.getWxMpConfigStorage()), imgUrl), null);
        return WxMpImgProcQrCodeResult.fromJson(result);
    }

    @Override
    public WxMpImgProcQrCodeResult qrCode(File imgFile) throws WxErrorException {
        String result = this.wxMpService.execute(OcrDiscernRequestExecutor.create(this.wxMpService.getRequestHttp()), FILE_QRCODE.getUrl(this.wxMpService.getWxMpConfigStorage()), imgFile);
        return WxMpImgProcQrCodeResult.fromJson(result);
    }

    @Override
    public WxMpImgProcSuperResolutionResult superResolution(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            //ignore
        }

        final String result = this.wxMpService.get(String.format(SUPER_RESOLUTION.getUrl(this.wxMpService.getWxMpConfigStorage()), imgUrl), null);
        return WxMpImgProcSuperResolutionResult.fromJson(result);
    }

    @Override
    public WxMpImgProcSuperResolutionResult superResolution(File imgFile) throws WxErrorException {
        String result = this.wxMpService.execute(OcrDiscernRequestExecutor.create(this.wxMpService.getRequestHttp()), FILE_SUPER_RESOLUTION.getUrl(this.wxMpService.getWxMpConfigStorage()), imgFile);
        return WxMpImgProcSuperResolutionResult.fromJson(result);
    }

    @Override
    public WxMpImgProcAiCropResult aiCrop(String imgUrl) throws WxErrorException {
        return this.aiCrop(imgUrl, "");
    }

    @Override
    public WxMpImgProcAiCropResult aiCrop(String imgUrl, String ratios) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            //ignore
        }

        if (StringUtils.isEmpty(ratios)) {
            ratios = "";
        }

        final String result = this.wxMpService.get(String.format(AI_CROP.getUrl(this.wxMpService.getWxMpConfigStorage()), imgUrl, ratios), null);
        return WxMpImgProcAiCropResult.fromJson(result);
    }

    @Override
    public WxMpImgProcAiCropResult aiCrop(File imgFile) throws WxErrorException {
        return this.aiCrop(imgFile, "");
    }

    @Override
    public WxMpImgProcAiCropResult aiCrop(File imgFile, String ratios) throws WxErrorException {
        if (StringUtils.isEmpty(ratios)) {
            ratios = "";
        }

        String result = this.wxMpService.execute(OcrDiscernRequestExecutor.create(this.wxMpService.getRequestHttp()), String.format(FILE_AI_CROP.getUrl(this.wxMpService.getWxMpConfigStorage()), ratios), imgFile);
        return WxMpImgProcAiCropResult.fromJson(result);
    }
}
