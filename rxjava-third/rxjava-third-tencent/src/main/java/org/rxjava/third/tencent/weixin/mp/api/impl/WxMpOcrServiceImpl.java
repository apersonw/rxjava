package org.rxjava.third.tencent.weixin.mp.api.impl;

import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpOcrService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.bean.ocr.*;
import org.rxjava.third.tencent.weixin.mp.util.requestexecuter.ocr.OcrDiscernRequestExecutor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.Ocr.*;

/**
 * ocr 接口实现.
 */
@RequiredArgsConstructor
public class WxMpOcrServiceImpl implements WxMpOcrService {
    private final WxMpService mainService;

    @Override
    public WxMpOcrIdCardResult idCard(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore cannot happen
        }

        final String result = this.mainService.get(String.format(IDCARD.getUrl(this.mainService.getWxMpConfigStorage()),
                imgUrl), null);
        return WxMpOcrIdCardResult.fromJson(result);
    }

    @Override
    public WxMpOcrIdCardResult idCard(File imgFile) throws WxErrorException {
        String result = this.mainService.execute(OcrDiscernRequestExecutor.create(this.mainService.getRequestHttp()),
                FILEIDCARD.getUrl(this.mainService.getWxMpConfigStorage()), imgFile);
        return WxMpOcrIdCardResult.fromJson(result);
    }

    @Override
    public WxMpOcrBankCardResult bankCard(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore cannot happen
        }

        final String result = this.mainService.get(String.format(BANK_CARD.getUrl(this.mainService.getWxMpConfigStorage()),
                imgUrl), null);
        return WxMpOcrBankCardResult.fromJson(result);
    }

    @Override
    public WxMpOcrBankCardResult bankCard(File imgFile) throws WxErrorException {
        String result = this.mainService.execute(OcrDiscernRequestExecutor.create(this.mainService.getRequestHttp()),
                FILE_BANK_CARD.getUrl(this.mainService.getWxMpConfigStorage()), imgFile);
        return WxMpOcrBankCardResult.fromJson(result);
    }

    @Override
    public WxMpOcrDrivingResult driving(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore cannot happen
        }

        final String result = this.mainService.get(String.format(DRIVING.getUrl(this.mainService.getWxMpConfigStorage()),
                imgUrl), null);
        return WxMpOcrDrivingResult.fromJson(result);
    }

    @Override
    public WxMpOcrDrivingResult driving(File imgFile) throws WxErrorException {
        String result = this.mainService.execute(OcrDiscernRequestExecutor.create(this.mainService.getRequestHttp()),
                FILE_DRIVING.getUrl(this.mainService.getWxMpConfigStorage()), imgFile);
        return WxMpOcrDrivingResult.fromJson(result);
    }

    @Override
    public WxMpOcrDrivingLicenseResult drivingLicense(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore cannot happen
        }

        final String result = this.mainService.get(String.format(DRIVING_LICENSE.getUrl(this.mainService.getWxMpConfigStorage()),
                imgUrl), null);
        return WxMpOcrDrivingLicenseResult.fromJson(result);
    }

    @Override
    public WxMpOcrDrivingLicenseResult drivingLicense(File imgFile) throws WxErrorException {
        String result = this.mainService.execute(OcrDiscernRequestExecutor.create(this.mainService.getRequestHttp()),
                FILE_DRIVING_LICENSE.getUrl(this.mainService.getWxMpConfigStorage()), imgFile);
        return WxMpOcrDrivingLicenseResult.fromJson(result);
    }

    @Override
    public WxMpOcrBizLicenseResult bizLicense(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore cannot happen
        }

        final String result = this.mainService.get(String.format(BIZ_LICENSE.getUrl(this.mainService.getWxMpConfigStorage()),
                imgUrl), null);
        return WxMpOcrBizLicenseResult.fromJson(result);
    }

    @Override
    public WxMpOcrBizLicenseResult bizLicense(File imgFile) throws WxErrorException {
        String result = this.mainService.execute(OcrDiscernRequestExecutor.create(this.mainService.getRequestHttp()),
                FILE_BIZ_LICENSE.getUrl(this.mainService.getWxMpConfigStorage()), imgFile);
        return WxMpOcrBizLicenseResult.fromJson(result);
    }

    @Override
    public WxMpOcrCommResult comm(String imgUrl) throws WxErrorException {
        try {
            imgUrl = URLEncoder.encode(imgUrl, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // ignore cannot happen
        }

        final String result = this.mainService.get(String.format(COMM.getUrl(this.mainService.getWxMpConfigStorage()),
                imgUrl), null);
        return WxMpOcrCommResult.fromJson(result);
    }

    @Override
    public WxMpOcrCommResult comm(File imgFile) throws WxErrorException {
        String result = this.mainService.execute(OcrDiscernRequestExecutor.create(this.mainService.getRequestHttp()),
                FILE_COMM.getUrl(this.mainService.getWxMpConfigStorage()), imgFile);
        return WxMpOcrCommResult.fromJson(result);
    }
}
