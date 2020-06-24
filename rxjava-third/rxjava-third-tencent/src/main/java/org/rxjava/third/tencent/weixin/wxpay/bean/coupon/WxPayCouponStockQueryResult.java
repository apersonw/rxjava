package org.rxjava.third.tencent.weixin.wxpay.bean.coupon;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.wxpay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

/**
 * 查询代金券批次响应结果类.
 * Created by Binary Wang on 2017-7-15.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayCouponStockQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 4644274730788451926L;
    /**
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 示例值：123456sb
     * 类型：String(32)
     * 说明：微信支付分配的终端设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：代金券批次ID.
     * 变量名：coupon_stock_id
     * 是否必填：是
     * 示例值：1757
     * 类型：String
     * 说明：代金券批次Id
     */
    @XStreamAlias("coupon_stock_id")
    private String couponStockId;

    /**
     * 字段名：代金券名称.
     * 变量名：coupon_name
     * 是否必填：否
     * 示例值：测试代金券
     * 类型：String
     * 说明：代金券名称
     */
    @XStreamAlias("coupon_name")
    private String couponName;

    /**
     * 字段名：代金券面额.
     * 变量名：coupon_value
     * 是否必填：是
     * 示例值：5
     * 类型：Unsinged int
     * 说明：代金券面值,单位是分
     */
    @XStreamAlias("coupon_value")
    private Integer couponValue;

    /**
     * 字段名：代金券使用最低限额.
     * 变量名：coupon_mininumn
     * 是否必填：否
     * 示例值：10
     * 类型：Unsinged int
     * 说明：代金券使用最低限额,单位是分
     */
    @XStreamAlias("coupon_mininumn")
    private Integer couponMinimum;

    /**
     * 字段名：代金券批次状态.
     * 变量名：coupon_stock_status
     * 是否必填：是
     * 示例值：4
     * 类型：int
     * 说明：批次状态： 1-未激活；2-审批中；4-已激活；8-已作废；16-中止发放；
     */
    @XStreamAlias("coupon_stock_status")
    private Integer couponStockStatus;

    /**
     * 字段名：代金券数量.
     * 变量名：coupon_total
     * 是否必填：是
     * 示例值：100
     * 类型：Unsigned int
     * 说明：代金券数量
     */
    @XStreamAlias("coupon_total")
    private Integer couponTotal;

    /**
     * 字段名：代金券最大领取数量.
     * 变量名：max_quota
     * 是否必填：否
     * 示例值：1
     * 类型：Unsigned int
     * 说明：代金券每个人最多能领取的数量, 如果为0，则表示没有限制
     */
    @XStreamAlias("max_quota")
    private Integer maxQuota;

    /**
     * 字段名：代金券已经发送的数量.
     * 变量名：is_send_num
     * 是否必填：否
     * 示例值：0
     * 类型：Unsigned int
     * 说明：代金券已经发送的数量
     */
    @XStreamAlias("is_send_num")
    private Integer isSendNum;

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
     * 示例值：1943787490
     * 类型：String
     * 说明：格式为时间戳
     */
    @XStreamAlias("end_time")
    private String endTime;

    /**
     * 字段名：创建时间.
     * 变量名：create_time
     * 是否必填：是
     * 示例值：1943787420
     * 类型：String
     * 说明：格式为时间戳
     */
    @XStreamAlias("create_time")
    private String createTime;

    /**
     * 字段名：代金券预算额度.
     * 变量名：coupon_budget
     * 是否必填：否
     * 示例值：500
     * 类型：Unsigned int
     * 说明：代金券预算额度
     */
    @XStreamAlias("coupon_budget")
    private Integer couponBudget;

    @Override
    protected void loadXML(Document d) {
        deviceInfo = readXMLString(d, "device_info");
        couponStockId = readXMLString(d, "coupon_stock_id");
        couponName = readXMLString(d, "coupon_name");
        couponValue = readXMLInteger(d, "coupon_value");
        couponMinimum = readXMLInteger(d, "coupon_mininumn");
        couponStockStatus = readXMLInteger(d, "coupon_stock_status");
        couponTotal = readXMLInteger(d, "coupon_total");
        maxQuota = readXMLInteger(d, "max_quota");
        isSendNum = readXMLInteger(d, "is_send_num");
        beginTime = readXMLString(d, "begin_time");
        endTime = readXMLString(d, "end_time");
        createTime = readXMLString(d, "create_time");
        couponBudget = readXMLInteger(d, "coupon_budget");
    }
}
