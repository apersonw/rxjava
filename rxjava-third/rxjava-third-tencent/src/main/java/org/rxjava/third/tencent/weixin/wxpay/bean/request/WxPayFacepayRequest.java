package org.rxjava.third.tencent.weixin.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.rxjava.third.tencent.weixin.common.annotation.Required;

import java.util.Map;

/**
 * 提交刷脸支付请求对象类
 * 详见文档：微信人脸支付商户开发文档
 * Created by Jmdhappy on 2019-09-05.
 *
 * @author <a href="https://github.com/jmdhappy/xxpay-master">XxPay</a>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayFacepayRequest extends BaseWxPayRequest {

    /**
     * 字段名：设备号.
     * 变量名：device_info
     * 是否必填：否
     * 类型：String(32)
     * 示例值：013467007045764
     * 描述：终端设备号(商户自定义，如门店编号)
     */
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 字段名：商品描述.
     * 变量名：body
     * 是否必填：是
     * 类型：String(128)
     * 示例值：image形象店-深圳腾大- QQ公仔
     * 描述：商品或支付单简要描述，格式要求：门店品牌名-城市分店名-实际商品名称
     **/
    @Required
    @XStreamAlias("body")
    private String body;

    /**
     * 字段名：商品详情.
     * 变量名：detail
     * 是否必填：否
     * 类型：String(8192)
     * 示例值：
     * 描述：商品详细列表，使用Json格式，传输签名前请务必使用CDATA标签将JSON文本串保护起来。
     **/
    @XStreamAlias("detail")
    private String detail;

    /**
     * 字段名：附加数据.
     * 变量名：attach
     * 是否必填：否
     * 类型：String(127)
     * 示例值：说明
     * 描述：附加数据，在查询API和支付通知中原样返回，该字段主要用于商户携带订单的自定义数据
     **/
    @XStreamAlias("attach")
    private String attach;

    /**
     * 字段名：商户订单号.
     * 变量名：out_trade_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1217752501201407033233368018
     * 描述：商户系统内部的订单号,32个字符内、可包含字母；更换授权码必须要换新的商户订单号 其他说明见商户订单号
     **/
    @Required
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 字段名：总金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：888
     * 描述：订单总金额，单位为分，只能为整数，详见支付金额
     **/
    @Required
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 字段名：货币类型.
     * 变量名：fee_type
     * 是否必填：否
     * 类型：String(16)
     * 示例值：CNY
     * 描述：符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     **/
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 字段名：终端IP.
     * 变量名：spbill_create_ip
     * 是否必填：是
     * 类型：String(16)
     * 示例值：127.0.0.1
     * 描述：调用微信支付API的机器IP
     **/
    @Required
    @XStreamAlias("spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 字段名：商品标记.
     * 变量名：goods_tag
     * 是否必填：否
     * 类型：String(32)
     * 示例值：1234
     * 描述：商品标记，代金券或立减优惠功能的参数，说明详见代金券或立减优惠
     **/
    @XStreamAlias("goods_tag")
    private String goodsTag;

    /**
     * 字段名：用户标识.
     * 变量名：openid
     * 是否必填：是
     * 类型：String(128)
     * 示例值：oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * 描述：用户在商户appid 下的唯一标识
     */
    @Required
    @XStreamAlias("openid")
    private String openid;

    /**
     * 字段名：人脸凭证.
     * 变量名：face_code
     * 是否必填：是
     * 类型：String(128)
     * 示例值：
     * 描述：人脸凭证，用于刷脸支付
     **/
    @Required
    @XStreamAlias("face_code")
    private String faceCode;

    @Override
    protected void checkConstraints() {
        //do nothing
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("device_info", deviceInfo);
        map.put("body", body);
        map.put("detail", detail);
        map.put("attach", attach);
        map.put("out_trade_no", outTradeNo);
        map.put("total_fee", totalFee.toString());
        map.put("fee_type", feeType);
        map.put("spbill_create_ip", spbillCreateIp);
        map.put("goods_tag", goodsTag);
        map.put("openid", openid);
        map.put("face_code", faceCode);
    }

}
