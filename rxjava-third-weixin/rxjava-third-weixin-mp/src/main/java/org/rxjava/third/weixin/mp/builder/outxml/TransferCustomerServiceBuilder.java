package org.rxjava.third.weixin.mp.builder.outxml;

import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.weixin.mp.bean.message.WxMpXmlOutTransferKefuMessage;

/**
 * 客服消息builder
 * 用法: WxMpXmlOutTransferKefuMessage m = WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE().kfAccount("").toUser("").build();
 */
public final class TransferCustomerServiceBuilder
        extends BaseBuilder<TransferCustomerServiceBuilder, WxMpXmlOutTransferKefuMessage> {
    private String kfAccount;

    public TransferCustomerServiceBuilder kfAccount(String kf) {
        this.kfAccount = kf;
        return this;
    }

    @Override
    public WxMpXmlOutTransferKefuMessage build() {
        WxMpXmlOutTransferKefuMessage m = new WxMpXmlOutTransferKefuMessage();
        setCommon(m);
        if (StringUtils.isNotBlank(this.kfAccount)) {
            WxMpXmlOutTransferKefuMessage.TransInfo transInfo = new WxMpXmlOutTransferKefuMessage.TransInfo();
            transInfo.setKfAccount(this.kfAccount);
            m.setTransInfo(transInfo);
        }

        return m;
    }
}