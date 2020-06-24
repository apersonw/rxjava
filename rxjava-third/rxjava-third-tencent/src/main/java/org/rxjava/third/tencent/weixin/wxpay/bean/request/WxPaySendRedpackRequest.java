package org.rxjava.third.tencent.weixin.wxpay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;

import java.util.Map;

/**
 * 发送红包请求参数对象.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPaySendRedpackRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = -2035425086824987567L;

    @Override
    protected String[] getIgnoredParamsForSign() {
        return new String[]{"sign_type", "sub_appid"};
    }

    /**
     * mch_billno.
     * 商户订单号（每个订单号必须唯一）
     * 组成：mch_id+yyyymmdd+10位一天内不能重复的数字。  接口根据商户订单号支持重入，如出现超时可再调用。
     */
    @XStreamAlias("mch_billno")
    private String mchBillNo;

    /**
     * send_name.
     * 商户名称
     * 红包发送者名称
     */
    @XStreamAlias("send_name")
    private String sendName;

    /**
     * re_openid.
     * 接受红包的用户   用户在wxappid下的openid
     */
    @XStreamAlias("re_openid")
    private String reOpenid;

    /**
     * total_amount.
     * 红包总额
     */
    @XStreamAlias("total_amount")
    private Integer totalAmount;

    /**
     * total_num
     * 红包发放总人数
     */
    @XStreamAlias("total_num")
    private Integer totalNum;

    /**
     * amt_type.
     * 红包金额设置方式
     * ALL_RAND—全部随机,商户指定总金额和红包发放总人数，由微信支付随机计算出各红包金额
     * 裂变红包必填
     */
    @XStreamAlias("amt_type")
    private String amtType;

    /**
     * wishing.
     * 红包祝福语
     */
    @XStreamAlias("wishing")
    private String wishing;

    /**
     * client_ip.
     * 服务器Ip地址
     * 调用接口的机器Ip地址
     */
    @XStreamAlias("client_ip")
    private String clientIp;

    /**
     * act_name.
     * 活动名称
     */
    @XStreamAlias("act_name")
    private String actName;

    /**
     * remark.
     * 备注
     */
    @XStreamAlias("remark")
    private String remark;

    /**
     * wxappid.
     * 微信分配的公众账号ID（企业号corpid即为此appId）。
     * 接口传入的所有appid应该为公众号的appid（在mp.weixin.qq.com申请的），
     * 不能为APP的appid（在open.weixin.qq.com申请的）
     */
    @XStreamAlias("wxappid")
    private String wxAppid;

    /**
     * 触达用户appid.
     * <p>
     * msgappid
     * wx28b16568a629bb33
     * String(32)
     * 服务商模式下触达用户时的appid(可填服务商自己的appid或子商户的appid)，
     * 服务商模式下必填，服务商模式下填入的子商户appid必须在微信支付商户平台中先录入，否则会校验不过。
     */
    @XStreamAlias("msgappid")
    private String msgAppid;

    /**
     * scene_id.
     * 场景id
     * PRODUCT_1:商品促销
     * PRODUCT_2:抽奖
     * PRODUCT_3:虚拟物品兑奖
     * PRODUCT_4:企业内部福利
     * PRODUCT_5:渠道分润
     * PRODUCT_6:保险回馈
     * PRODUCT_7:彩票派奖
     * PRODUCT_8:税务刮奖
     * 非必填字段
     */
    @XStreamAlias("scene_id")
    private String sceneId;

    /**
     * risk_info.
     * 活动信息
     * posttime:用户操作的时间戳
     * mobile:业务系统账号的手机号，国家代码-手机号。不需要+号
     * deviceid :mac 地址或者设备唯一标识
     * clientversion :用户操作的客户端版本
     * 把值为非空的信息用key=value进行拼接，再进行urlencode
     * urlencode(posttime=xx&mobile=xx&deviceid=xx)
     * 非必填字段
     */
    @XStreamAlias("risk_info")
    private String riskInfo;

    /**
     * consume_mch_id.
     * 资金授权商户号
     * 资金授权商户号
     * 服务商替特约商户发放时使用
     * 非必填字段
     */
    @XStreamAlias("consume_mch_id")
    private String consumeMchId;

    @Override
    protected void checkConstraints() {

    }

    @Override
    public String getAppid() {
        return this.wxAppid;
    }

    @Override
    public void setAppid(String appid) {
        this.wxAppid = appid;
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("mch_billno", mchBillNo);
        map.put("send_name", sendName);
        map.put("re_openid", reOpenid);
        map.put("total_amount", totalAmount.toString());
        map.put("total_num", totalNum.toString());
        map.put("amt_type", amtType);
        map.put("wishing", wishing);
        map.put("client_ip", clientIp);
        map.put("act_name", actName);
        map.put("remark", remark);
    }

}
