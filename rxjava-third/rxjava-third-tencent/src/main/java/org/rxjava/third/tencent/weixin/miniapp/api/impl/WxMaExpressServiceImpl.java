package org.rxjava.third.tencent.weixin.miniapp.api.impl;

import lombok.AllArgsConstructor;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaExpressService;
import org.rxjava.third.tencent.weixin.miniapp.api.WxMaService;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.WxMaExpressAccount;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.WxMaExpressDelivery;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.WxMaExpressPath;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.WxMaExpressPrinter;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.request.*;
import org.rxjava.third.tencent.weixin.miniapp.bean.express.result.WxMaExpressOrderInfoResult;
import org.rxjava.third.tencent.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="https://github.com/mr-xiaoyu">xiaoyu</a>
 * @since 2019-11-26
 */
@AllArgsConstructor
public class WxMaExpressServiceImpl implements WxMaExpressService {

    private WxMaService wxMaService;

    @Override
    public List<WxMaExpressDelivery> getAllDelivery() throws WxErrorException {
        String responseContent = this.wxMaService.get(ALL_DELIVERY_URL, null);
        return WxMaExpressDelivery.fromJson(responseContent);
    }

    @Override
    public List<WxMaExpressAccount> getAllAccount() throws WxErrorException {
        String responseContent = this.wxMaService.get(ALL_ACCOUNT_URL, null);
        return WxMaExpressAccount.fromJsonList(responseContent);
    }

    @Override
    public void bindAccount(WxMaExpressBindAccountRequest wxMaExpressBindAccountRequest) throws WxErrorException {
        this.wxMaService.post(BIND_ACCOUNT_URL, wxMaExpressBindAccountRequest.toJson());
    }

    @Override
    public Integer getQuota(WxMaExpressBindAccountRequest wxMaExpressBindAccountRequest) throws WxErrorException {
        String responseContent = this.wxMaService.post(GET_QUOTA_URL, wxMaExpressBindAccountRequest.toJson());
        WxMaExpressAccount account = WxMaExpressAccount.fromJson(responseContent);
        return account.getQuotaNum();
    }

    @Override
    public void updatePrinter(WxMaExpressPrinterUpdateRequest wxMaExpressPrinterUpdateRequest) throws WxErrorException {
        this.wxMaService.post(UPDATE_PRINTER_URL, wxMaExpressPrinterUpdateRequest.toJson());
    }

    @Override
    public WxMaExpressPrinter getPrinter() throws WxErrorException {
        String responseContent = this.wxMaService.get(GET_PRINTER_URL, null);
        return WxMaExpressPrinter.fromJson(responseContent);
    }

    @Override
    public WxMaExpressOrderInfoResult addOrder(WxMaExpressAddOrderRequest wxMaExpressAddOrderRequest) throws WxErrorException {
        String responseContent = this.wxMaService.post(ADD_ORDER_URL, wxMaExpressAddOrderRequest.toJson());
        return WxMaExpressOrderInfoResult.fromJson(responseContent);
    }

    @Override
    public List<WxMaExpressOrderInfoResult> batchGetOrder(List<WxMaExpressGetOrderRequest> requests) throws WxErrorException {
        Map<String, Object> param = new HashMap<>(1);
        param.put("order_list", requests);
        String responseContent = this.wxMaService.post(BATCH_GET_ORDER_URL, WxMaGsonBuilder.create().toJson(param));
        return WxMaExpressOrderInfoResult.toList(responseContent);
    }

    @Override
    public void cancelOrder(WxMaExpressGetOrderRequest wxMaExpressGetOrderRequest) throws WxErrorException {
        this.wxMaService.post(CANCEL_ORDER_URL, wxMaExpressGetOrderRequest.toJson());
    }

    @Override
    public WxMaExpressOrderInfoResult getOrder(WxMaExpressGetOrderRequest wxMaExpressGetOrderRequest) throws WxErrorException {
        String responseContent = this.wxMaService.post(GET_ORDER_URL, wxMaExpressGetOrderRequest.toJson());
        return WxMaExpressOrderInfoResult.fromJson(responseContent);
    }

    @Override
    public WxMaExpressPath getPath(WxMaExpressGetOrderRequest wxMaExpressGetOrderRequest) throws WxErrorException {
        String responseContent = this.wxMaService.post(GET_PATH_URL, wxMaExpressGetOrderRequest.toJson());
        return WxMaExpressPath.fromJson(responseContent);
    }

    @Override
    public void testUpdateOrder(WxMaExpressTestUpdateOrderRequest wxMaExpressTestUpdateOrderRequest) throws WxErrorException {
        this.wxMaService.post(TEST_UPDATE_ORDER_URL, wxMaExpressTestUpdateOrderRequest.toJson());
    }
}
