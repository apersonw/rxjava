package top.rxjava.third.weixin.pay.bean.result;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 微信支付-申请退款返回结果.
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayRefundResult extends BaseWxPayResult implements Serializable {
    private static final long serialVersionUID = -3392333879907788033L;
    /**
     * 微信订单号.
     */
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 商户订单号.
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 商户退款单号.
     */
    @XStreamAlias("out_refund_no")
    private String outRefundNo;

    /**
     * 微信退款单号.
     */
    @XStreamAlias("refund_id")
    private String refundId;

    /**
     * 退款金额.
     */
    @XStreamAlias("refund_fee")
    private Integer refundFee;

    /**
     * 应结退款金额.
     */
    @XStreamAlias("settlement_refund_fee")
    private Integer settlementRefundFee;

    /**
     * 标价金额.
     */
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 应结订单金额.
     */
    @XStreamAlias("settlement_total_fee")
    private Integer settlementTotalFee;

    /**
     * 标价币种.
     */
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 现金支付金额.
     */
    @XStreamAlias("cash_fee")
    private Integer cashFee;

    /**
     * 现金支付币种.
     */
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    /**
     * 现金退款金额，单位为分，只能为整数，详见支付金额.
     */
    @XStreamAlias("cash_refund_fee")
    private Integer cashRefundFee;

    /**
     * 退款代金券使用数量.
     */
    @XStreamAlias("coupon_refund_count")
    private Integer couponRefundCount;

    /**
     * 字段名：代金券退款总金额.
     * 变量名：coupon_refund_fee
     * 是否必填：否
     * 类型：Int
     * 示例值：100
     * 描述：代金券退款金额<=退款金额，退款金额-代金券或立减优惠退款金额为现金，说明详见代金券或立减优惠
     */
    @XStreamAlias("coupon_refund_fee")
    private Integer couponRefundFee;

    private List<WxPayRefundCouponInfo> refundCoupons;

    /**
     * 组装生成退款代金券信息.
     */
    public void composeRefundCoupons() {
        List<WxPayRefundCouponInfo> coupons = Lists.newArrayList();
        Integer refundCount = this.getCouponRefundCount();
        if (refundCount == null) {
            //无退款代金券信息
            return;
        }

        for (int i = 0; i < refundCount; i++) {
            coupons.add(
                    new WxPayRefundCouponInfo(
                            this.getXmlValue("xml/coupon_refund_id_" + i),
                            this.getXmlValueAsInt("xml/coupon_refund_fee_" + i),
                            this.getXmlValue("xml/coupon_type_" + i)
                    )
            );
        }

        this.setRefundCoupons(coupons);
    }

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        transactionId = readXMLString(d, "transaction_id");
        outTradeNo = readXMLString(d, "out_trade_no");
        outRefundNo = readXMLString(d, "out_refund_no");
        refundId = readXMLString(d, "refund_id");
        refundFee = readXMLInteger(d, "refund_fee");
        settlementRefundFee = readXMLInteger(d, "settlement_refund_fee");
        totalFee = readXMLInteger(d, "total_fee");
        settlementTotalFee = readXMLInteger(d, "settlement_total_fee");
        feeType = readXMLString(d, "fee_type");
        cashFee = readXMLInteger(d, "cash_fee");
        cashFeeType = readXMLString(d, "cash_fee_type");
        cashRefundFee = readXMLInteger(d, "cash_refund_fee");
        couponRefundCount = readXMLInteger(d, "coupon_refund_count");
        couponRefundFee = readXMLInteger(d, "coupon_refund_fee");
    }

}
