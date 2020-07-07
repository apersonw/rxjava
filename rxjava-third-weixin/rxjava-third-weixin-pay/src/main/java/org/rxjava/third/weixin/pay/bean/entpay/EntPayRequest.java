package org.rxjava.third.weixin.pay.bean.entpay;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.rxjava.third.weixin.common.annotation.Required;
import org.rxjava.third.weixin.pay.bean.request.BaseWxPayRequest;

import java.util.Map;

/**
 * 企业付款请求对象.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class EntPayRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 8647710192770447579L;

    /**
     * 字段名：公众账号appid.
     * 变量名：mch_appid
     * 是否必填：是
     * 示例值：wx8888888888888888
     * 类型：String
     * 描述：微信分配的公众账号ID（企业号corpid即为此appId）
     */
    @XStreamAlias("mch_appid")
    private String mchAppid;

    /**
     * 字段名：商户号.
     * 变量名：mchid
     * 是否必填：是
     * 示例值：1900000109
     * 类型：String(32)
     * 描述：微信支付分配的商户号
     */
    @XStreamAlias("mchid")
    private String mchId;

    /**
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 示例值：13467007045764
     * 类型：String(32)
     * 描述：微信支付分配的终端设备号
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：商户订单号.
     * 变量名：partner_trade_no
     * 是否必填：是
     * 示例值：10000098201411111234567890
     * 类型：String
     * 描述：商户订单号
     */
    @Required
    @XStreamAlias("partner_trade_no")
    private String partnerTradeNo;

    /**
     * 字段名：需保持唯一性 用户openid.
     * 变量名：openid
     * 是否必填：是
     * 示例值：oxTWIuGaIt6gTKsQRLau2M0yL16E
     * 类型：String
     * 描述：商户appid下，某用户的openid
     */
    @Required
    @XStreamAlias("openid")
    private String openid;

    /**
     * 字段名：校验用户姓名选项.
     * 变量名：check_name
     * 是否必填：是
     * 示例值：OPTION_CHECK
     * 类型：String
     * 描述：NO_CHECK：不校验真实姓名 
     * FORCE_CHECK：强校验真实姓名（未实名认证的用户会校验失败，无法转账） 
     * OPTION_CHECK：针对已实名认证的用户才校验真实姓名（未实名认证用户不校验，可以转账成功）
     */
    @Required
    @XStreamAlias("check_name")
    private String checkName;

    /**
     * 字段名：收款用户姓名.
     * 变量名：re_user_name
     * 是否必填：可选
     * 示例值：马花花
     * 类型：String
     * 描述：收款用户真实姓名。
     * 如果check_name设置为FORCE_CHECK或OPTION_CHECK，  则必填用户真实姓名
     */
    @XStreamAlias("re_user_name")
    private String reUserName;

    /**
     * 字段名：金额.
     * 变量名：amount
     * 是否必填：是
     * 示例值：10099
     * 类型：int
     * 描述：企业付款金额， 单位为分
     */
    @Required
    @XStreamAlias("amount")
    private Integer amount;

    /**
     * 字段名：企业付款描述信息.
     * 变量名：desc
     * 是否必填：是
     * 示例值：理赔
     * 类型：String
     * 描述：企业付款操作说明信息。必填。
     */
    @Required
    @XStreamAlias("desc")
    private String description;

    /**
     * 字段名：Ip地址.
     * 变量名：spbill_create_ip
     * 是否必填：是
     * 示例值：192.168.0.1
     * 类型：String(32)
     * 描述：调用接口的机器Ip地址
     */
    @Required
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    @Override
    protected void checkConstraints() {

    }

    @Override
    public String getAppid() {
        return this.mchAppid;
    }

    @Override
    public void setAppid(String appid) {
        this.mchAppid = appid;
    }

    @Override
    public String getMchId() {
        return this.mchId;
    }

    @Override
    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"sign_type"};
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("mch_appid", mchAppid);
        map.put("mchid", mchId);
        map.put("device_info", deviceInfo);
        map.put("partner_trade_no", partnerTradeNo);
        map.put("openid", openid);
        map.put("check_name", checkName);
        map.put("re_user_name", reUserName);
        map.put("amount", amount.toString());
        map.put("desc", description);
        map.put("spbill_create_ip", spbillCreateIp);
    }
}
