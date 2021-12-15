package top.rxjava.third.weixin.pay.service.impl;

import lombok.RequiredArgsConstructor;
import top.rxjava.third.weixin.pay.bean.request.WxPayRedpackQueryRequest;
import top.rxjava.third.weixin.pay.bean.request.WxPaySendMiniProgramRedpackRequest;
import top.rxjava.third.weixin.pay.bean.request.WxPaySendRedpackRequest;
import top.rxjava.third.weixin.pay.bean.result.BaseWxPayResult;
import top.rxjava.third.weixin.pay.bean.result.WxPayRedpackQueryResult;
import top.rxjava.third.weixin.pay.bean.result.WxPaySendMiniProgramRedpackResult;
import top.rxjava.third.weixin.pay.bean.result.WxPaySendRedpackResult;
import top.rxjava.third.weixin.pay.constant.WxPayConstants;
import top.rxjava.third.weixin.pay.exception.WxPayException;
import top.rxjava.third.weixin.pay.service.RedpackService;
import top.rxjava.third.weixin.pay.service.WxPayService;

/**
 */
@RequiredArgsConstructor
public class RedpackServiceImpl implements RedpackService {
    private final WxPayService payService;

    @Override
    public WxPaySendMiniProgramRedpackResult sendMiniProgramRedpack(WxPaySendMiniProgramRedpackRequest request)
            throws WxPayException {
        request.checkAndSign(this.payService.getConfig());
        String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/sendminiprogramhb";
        String responseContent = this.payService.post(url, request.toXML(), true);

        WxPaySendMiniProgramRedpackResult result = BaseWxPayResult.fromXML(responseContent, WxPaySendMiniProgramRedpackResult.class);
        result.checkResult(this.payService, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPaySendRedpackResult sendRedpack(WxPaySendRedpackRequest request) throws WxPayException {
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/sendredpack";
        if (request.getAmtType() != null) {
            //裂变红包
            url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/sendgroupredpack";
        }

        String responseContent = this.payService.post(url, request.toXML(), true);
        final WxPaySendRedpackResult result = BaseWxPayResult.fromXML(responseContent, WxPaySendRedpackResult.class);
        result.checkResult(this.payService, request.getSignType(), true);
        return result;
    }

    @Override
    public WxPayRedpackQueryResult queryRedpack(String mchBillNo) throws WxPayException {
        WxPayRedpackQueryRequest request = new WxPayRedpackQueryRequest();
        request.setMchBillNo(mchBillNo);
        return this.queryRedpack(request);
    }

    @Override
    public WxPayRedpackQueryResult queryRedpack(WxPayRedpackQueryRequest request) throws WxPayException {
        request.setBillType(WxPayConstants.BillType.MCHT);
        request.checkAndSign(this.payService.getConfig());

        String url = this.payService.getPayBaseUrl() + "/mmpaymkttransfers/gethbinfo";
        String responseContent = this.payService.post(url, request.toXML(), true);
        WxPayRedpackQueryResult result = BaseWxPayResult.fromXML(responseContent, WxPayRedpackQueryResult.class);
        result.checkResult(this.payService, request.getSignType(), true);
        return result;
    }
}
