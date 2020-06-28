package org.rxjava.third.weixin.pay.bean.result;

import com.google.common.collect.Lists;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.List;

/**
 * 查询订单 返回结果对象
 * Created by Binary Wang on 2016-10-24.
 * 注释中各行每个字段描述对应如下：
 * 字段名
 * 变量名
 * 是否必填
 * 类型
 * 示例值
 * 描述
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayOrderQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 8241891654782412789L;

    /**
     * 字段名：营销详情.
     * 变量名：promotion_detail
     * 是否必填：否，单品优惠才有
     * 类型：String(6000)
     * 示例值：[{"promotion_detail":[{"promotion_id":"109519","name":"单品惠-6","scope":"SINGLE","type":"DISCOUNT","amount":5,"activity_id":"931386","wxpay_contribute":0,"merchant_contribute":0,"other_contribute":5,"goods_detail":[{"goods_id":"a_goods1","goods_remark":"商品备注","quantity":7,"price":1,"discount_amount":4},{"goods_id":"a_goods2","goods_remark":"商品备注","quantity":1,"price":2,"discount_amount":1}]}]}
     * 描述：单品优惠专用参数，详见https://pay.weixin.qq.com/wiki/doc/api/danpin.php?chapter=9_201&index=3
     */
    @XStreamAlias("promotion_detail")
    private String promotionDetail;

    /**
     * 设备号.
     * device_info
     * 否
     * String(32)
     * 013467007045764
     * 微信支付分配的终端设备号，
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 用户标识.
     * openid
     * 是
     * String(128)
     * oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 用户在商户appid下的唯一标识
     */
    @XStreamAlias("openid")
    private String openid;

    /**
     * 是否关注公众账号.
     * is_subscribe
     * 是
     * String(1)
     * Y
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    /**
     * 用户子标识	.
     * sub_openid
     * 否
     * String(128)
     * oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 用户在子商户appid下的唯一标识
     */
    @XStreamAlias("sub_openid")
    private String subOpenid;

    /**
     * 是否关注子公众账号.
     * sub_is_subscribe
     * 否
     * String(1)
     * Y
     * 用户是否关注子公众账号，Y-关注，N-未关注（机构商户不返回）
     */
    @XStreamAlias("sub_is_subscribe")
    private String isSubscribeSub;

    /**
     * 交易类型.
     * trade_type
     * 是
     * String(16)
     * JSAPI
     * 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定
     */
    @XStreamAlias("trade_type")
    private String tradeType;

    /**
     * 交易状态.
     * trade_state
     * 是
     * String(32)
     * SUCCESS
     * SUCCESS—支付成功,REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    @XStreamAlias("trade_state")
    private String tradeState;

    /**
     * 付款银行.
     * bank_type
     * 是
     * String(16)
     * CMC
     * 银行类型，采用字符串类型的银行标识
     */
    @XStreamAlias("bank_type")
    private String bankType;

    /**
     * 商品详情.
     * detail
     * 否
     * String(8192)
     * 商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。如果使用了单品优惠，会有单品优惠信息返回
     * <p>
     * discount_detail []：
     * └ goods_id String 必填 32 商品的编号
     * └ goods_name String 必填 256 商品名称
     * └ coupon_batch_id String 必填 代金券批次ID
     * └ coupon_id String 必填 代金卷ID
     * └ coupon_fee Int 必填 代金券支付金额，单位为分
     **/
    @XStreamAlias("detail")
    private String detail;

    /**
     * 订单金额.
     * total_fee
     * 是
     * Int
     * 100
     * 订单总金额，单位为分
     */
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 货币种类.
     * fee_type
     * 否
     * String(8)
     * CNY
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 应结订单金额.
     * settlement_total_fee
     * 否
     * Int
     * 100
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     */
    @XStreamAlias("settlement_total_fee")
    private Integer settlementTotalFee;

    /**
     * 现金支付金额.
     * cash_fee
     * 是
     * Int
     * 100
     * 现金支付金额订单现金支付金额，详见支付金额
     */
    @XStreamAlias("cash_fee")
    private Integer cashFee;

    /**
     * 现金支付货币类型.
     * cash_fee_type
     * 否
     * String(16)
     * CNY
     * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     */
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    /**
     * 代金券金额.
     * coupon_fee
     * 否
     * Int
     * 100
     * “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额
     */
    @XStreamAlias("coupon_fee")
    private Integer couponFee;

    /**
     * 代金券使用数量.
     * coupon_count
     * 否
     * Int
     * 1
     * 代金券使用数量
     */
    @XStreamAlias("coupon_count")
    private Integer couponCount;

    private List<Coupon> coupons;
    /**
     * 微信支付订单号.
     * transaction_id
     * 是
     * String(32)
     * 1009660380201506130728806387
     * 微信支付订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;
    /**
     * 商户订单号.
     * out_trade_no
     * 是
     * String(32)
     * 20150806125346
     * 商户系统的订单号，与请求一致。
     */
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 附加数据.
     * attach
     * 否
     * String(128)
     * 深圳分店
     * 附加数据，原样返回
     */
    @XStreamAlias("attach")
    private String attach;

    /**
     * 支付完成时间.
     * time_end
     * 是
     * String(14)
     * 20141030133525
     * 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     */
    @XStreamAlias("time_end")
    private String timeEnd;

    /**
     * 交易状态描述.
     * trade_state_desc
     * 是
     * String(256)
     * 支付失败，请重新下单支付
     * 对当前查询订单状态的描述和下一步操作的指引
     */
    @XStreamAlias("trade_state_desc")
    private String tradeStateDesc;

    /**
     * 通过xml组装coupons属性内容.
     */
    public void composeCoupons() {
        if (this.couponCount != null && this.couponCount > 0) {
            this.coupons = Lists.newArrayList();
            for (int i = 0; i < this.couponCount; i++) {
                this.coupons.add(new Coupon(this.getXmlValue("xml/coupon_type_" + i),
                        this.getXmlValue("xml/coupon_id_" + i),
                        this.getXmlValueAsInt("xml/coupon_fee_" + i)));
            }
        }
    }

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        promotionDetail = readXMLString(d, "promotion_detail");
        deviceInfo = readXMLString(d, "device_info");
        openid = readXMLString(d, "openid");
        isSubscribe = readXMLString(d, "is_subscribe");
        tradeType = readXMLString(d, "trade_type");
        tradeState = readXMLString(d, "trade_state");
        bankType = readXMLString(d, "bank_type");
        totalFee = readXMLInteger(d, "total_fee");
        settlementTotalFee = readXMLInteger(d, "settlement_total_fee");
        feeType = readXMLString(d, "fee_type");
        cashFee = readXMLInteger(d, "cash_fee");
        cashFeeType = readXMLString(d, "cash_fee_type");
        couponFee = readXMLInteger(d, "coupon_fee");
        couponCount = readXMLInteger(d, "coupon_count");
        this.transactionId = readXMLString(d, "transaction_id");
        this.outTradeNo = readXMLString(d, "out_trade_no");
        this.attach = readXMLString(d, "attach");
        this.timeEnd = readXMLString(d, "time_end");
        this.tradeStateDesc = readXMLString(d, "trade_state_desc");
    }

    /**
     * The type Coupon.
     */
    @Data
    @Builder(builderMethodName = "newBuilder")
    @AllArgsConstructor
    public static class Coupon implements Serializable {
        private static final long serialVersionUID = -954000582332155081L;

        /**
         * 代金券类型.
         * coupon_type_$n
         * 否
         * String
         * CASH
         * <li>CASH--充值代金券
         * <li>NO_CASH---非充值代金券
         * 订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
         */
        private String couponType;

        /**
         * 代金券ID.
         * coupon_id_$n
         * 否
         * String(20)
         * 10000
         * 代金券ID, $n为下标，从0开始编号
         */
        private String couponId;

        /**
         * 单个代金券支付金额.
         * coupon_fee_$n
         * 否
         * Int
         * 100
         * 单个代金券支付金额, $n为下标，从0开始编号
         */
        private Integer couponFee;

    }

}
