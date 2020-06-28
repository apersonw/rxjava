package org.rxjava.third.weixin.pay.bean.coupon;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.weixin.pay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 查询代金券信息响应结果类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayCouponInfoQueryResult extends BaseWxPayResult {
    /**
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 示例值：123456sb
     * 类型：String(32)
     * 说明：微信支付分配的终端设备号，
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：批次ID.
     * 变量名：coupon_stock_id
     * 是否必填：是
     * 示例值：1567
     * 类型：String
     * 说明：代金券批次Id
     */
    @XStreamAlias("coupon_stock_id")
    private String couponStockId;

    /**
     * 字段名：代金券id.
     * 变量名：coupon_id
     * 是否必填：是
     * 示例值：4242
     * 类型：String
     * 说明：代金券id
     */
    @XStreamAlias("coupon_id")
    private String couponId;

    /**
     * 字段名：代金券面额.
     * 变量名：coupon_value
     * 是否必填：是
     * 示例值：4
     * 类型：Unsinged int
     * 说明：代金券面值,单位是分
     */
    @XStreamAlias("coupon_value")
    private Integer couponValue;

    /**
     * 字段名：代金券使用门槛.
     * 变量名：coupon_minimum 微信文档有误
     * 是否必填：是
     * 示例值：10
     * 类型：Unsinged int
     * 说明：代金券使用最低限额,单位是分
     */
    @XStreamAlias("coupon_minimum")
    private Integer couponMinimum;

    /**
     * 字段名：代金券名称.
     * 变量名：coupon_name
     * 是否必填：是
     * 示例值：测试代金券
     * 类型：String
     * 说明：代金券名称
     */
    @XStreamAlias("coupon_name")
    private String couponName;

    /**
     * 字段名：代金券状态.
     * 变量名：coupon_state
     * 是否必填：是
     * 示例值：SENDED
     * 类型：String
     * 说明：代金券状态：SENDED-可用，USED-已实扣，EXPIRED-已过期
     */
    @XStreamAlias("coupon_state")
    private String couponState;

    /**
     * 字段名：代金券描述.
     * 变量名：coupon_desc
     * 是否必填：是
     * 示例值：微信支付-代金券
     * 类型：String
     * 说明：代金券描述
     */
    @XStreamAlias("coupon_desc")
    private String couponDesc;

    /**
     * 字段名：实际优惠金额.
     * 变量名：coupon_use_value
     * 是否必填：是
     * 示例值：0
     * 类型：Unsinged int
     * 说明：代金券实际使用金额
     */
    @XStreamAlias("coupon_use_value")
    private Integer couponUseValue;

    /**
     * 字段名：优惠剩余可用额.
     * 变量名：coupon_remain_value
     * 是否必填：是
     * 示例值：4
     * 类型：Unsinged int
     * 说明：代金券剩余金额：部分使用情况下，可能会存在券剩余金额
     */
    @XStreamAlias("coupon_remain_value")
    private Integer couponRemainValue;

    /**
     * 字段名：生效开始时间.
     * 变量名：begin_time
     * 是否必填：是
     * 示例值：1943787483
     * 类型：String
     * 说明：格式为时间戳
     */
    @XStreamAlias("begin_time")
    private String beginTime;

    /**
     * 字段名：生效结束时间.
     * 变量名：end_time
     * 是否必填：是
     * 示例值：1943787484
     * 类型：String
     * 说明：格式为时间戳
     */
    @XStreamAlias("end_time")
    private String endTime;

    /**
     * 字段名：发放时间.
     * 变量名：send_time
     * 是否必填：是
     * 示例值：1943787420
     * 类型：String
     * 说明：格式为时间戳
     */
    @XStreamAlias("send_time")
    private String sendTime;

    /**
     * 字段名：消耗方商户id.
     * 变量名：consumer_mch_id
     * 是否必填：否
     * 示例值：10000098
     * 类型：String
     * 说明：代金券使用后，消耗方商户id
     */
    @XStreamAlias("consumer_mch_id")
    private String consumerMchId;

    /**
     * 字段名：发放来源.
     * 变量名：send_source
     * 是否必填：是
     * 示例值：FULL_SEND
     * 类型：String
     * 说明：代金券发放来源：FULL_SEND-满送 NORMAL-普通发放场景
     */
    @XStreamAlias("send_source")
    private String sendSource;

    /**
     * 字段名：是否允许部分使用.
     * 变量名：is_partial_use
     * 是否必填：否
     * 示例值：1
     * 类型：String
     * 说明：该代金券是否允许部分使用标识：1-表示支持部分使用
     */
    @XStreamAlias("is_partial_use")
    private String isPartialUse;

    @Override
    protected void loadXML(Document d) {
        deviceInfo = readXMLString(d, "device_info");
        couponStockId = readXMLString(d, "coupon_stock_id");
        couponId = readXMLString(d, "coupon_id");
        couponValue = readXMLInteger(d, "coupon_value");
        couponMinimum = readXMLInteger(d, "coupon_minimum");
        couponName = readXMLString(d, "coupon_name");
        couponState = readXMLString(d, "coupon_state");
        couponDesc = readXMLString(d, "coupon_desc");
        couponUseValue = readXMLInteger(d, "coupon_use_value");
        couponRemainValue = readXMLInteger(d, "coupon_remain_value");
        beginTime = readXMLString(d, "begin_time");
        endTime = readXMLString(d, "end_time");
        sendTime = readXMLString(d, "send_time");
        consumerMchId = readXMLString(d, "consumer_mch_id");
        sendSource = readXMLString(d, "send_source");
        isPartialUse = readXMLString(d, "is_partial_use");
    }
}
