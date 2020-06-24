package org.rxjava.third.tencent.weixin.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

/**
 * 提交刷卡支付接口响应结果对象类
 * Created by Binary Wang on 2017-3-23.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayMicropayResult extends BaseWxPayResult {
    private static final long serialVersionUID = 529670965722059189L;

    /**
     * 用户标识.
     * openid
     * 是
     * String(128)
     * Y
     * 用户在商户appid 下的唯一标识
     **/
    @XStreamAlias("openid")
    private String openid;

    /**
     * 是否关注公众账号.
     * is_subscribe
     * 是
     * String(1)
     * Y
     * 用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注
     **/
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    /**
     * 交易类型.
     * trade_type
     * 是
     * String(16)
     * MICROPAY
     * 支付类型为MICROPAY(即扫码支付)
     **/
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 付款银行.
     * bank_type
     * 是
     * String(32)
     * CMC
     * 银行类型，采用字符串类型的银行标识，值列表详见银行类型
     **/
    @XStreamAlias("bank_type")
    private String bankType;

    /**
     * 货币类型.
     * fee_type
     * 否
     * String(16)
     * CNY
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     **/
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 订单金额.
     * total_fee
     * 是
     * Int
     * 888
     * 订单总金额，单位为分，只能为整数，详见支付金额
     **/
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 应结订单金额.
     * settlement_total_fee
     * 否
     * Int
     * 100
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     **/
    @XStreamAlias("settlement_total_fee")
    private Integer settlementTotalFee;

    /**
     * 代金券金额.
     * coupon_fee
     * 否
     * Int
     * 100
     * “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额
     **/
    @XStreamAlias("coupon_fee")
    private Integer couponFee;

    /**
     * 现金支付货币类型.
     * cash_fee_type
     * 否
     * String(16)
     * CNY
     * 符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     **/
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    /**
     * 现金支付金额.
     * cash_fee
     * 是
     * Int
     * 100
     * 订单现金支付金额，详见支付金额
     **/
    @XStreamAlias("cash_fee")
    private Integer cashFee;

    /**
     * 微信支付订单号.
     * transaction_id
     * 是
     * String(32)
     * 1217752501201407033233368018
     * 微信支付订单号
     **/
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 商户订单号.
     * out_trade_no
     * 是
     * String(32)
     * 1217752501201407033233368018
     * 商户系统的订单号，与请求一致。
     **/
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 商家数据包.
     * attach
     * 否
     * String(128)
     * 123456
     * 商家数据包，原样返回
     **/
    @XStreamAlias("attach")
    private String attach;

    /**
     * 支付完成时间.
     * time_end
     * 是
     * String(14)
     * 20141030133525
     * 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。详见时间规则
     **/
    @XStreamAlias("time_end")
    private String timeEnd;

    /**
     * 营销详情.
     * promotion_detail
     * 否
     * String(6000)
     * 示例见下文
     * 新增返回，单品优惠功能字段，需要接入请见详细说明
     **/
    @XStreamAlias("promotion_detail")
    private String promotionDetail;

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        openid = readXMLString(d, "openid");
        isSubscribe = readXMLString(d, "is_subscribe");
        tradeType = readXMLString(d, "trade_type");
        bankType = readXMLString(d, "bank_type");
        feeType = readXMLString(d, "fee_type");
        totalFee = readXMLInteger(d, "total_fee");
        settlementTotalFee = readXMLInteger(d, "settlement_total_fee");
        couponFee = readXMLInteger(d, "coupon_fee");
        cashFeeType = readXMLString(d, "cash_fee_type");
        cashFee = readXMLInteger(d, "cash_fee");
        transactionId = readXMLString(d, "transaction_id");
        outTradeNo = readXMLString(d, "out_trade_no");
        attach = readXMLString(d, "attach");
        timeEnd = readXMLString(d, "time_end");
        promotionDetail = readXMLString(d, "promotion_detail");
    }

}
