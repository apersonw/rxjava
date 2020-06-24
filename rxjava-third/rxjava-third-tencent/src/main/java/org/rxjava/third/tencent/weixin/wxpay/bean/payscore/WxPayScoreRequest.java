package org.rxjava.third.tencent.weixin.wxpay.bean.payscore;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class WxPayScoreRequest implements Serializable {
    private static final long serialVersionUID = 364764508076146082L;

    /**
     * out_order_no : 1234323JKHDFE1243252
     * appid : wxd678efh567hg6787
     * service_id : 500001
     * service_introduction : 某某酒店
     * post_payments : [{"name":"就餐费用服务费","amount":4000,"description":"就餐人均100元服务费：100/小时","count":1}]
     * post_discounts : [{"name":"满20减1元","description":"不与其他优惠叠加"}]
     * time_range : {"start_time":"20091225091010","end_time":"20091225121010"}
     * location : {"start_location":"嗨客时尚主题展餐厅","end_location":"嗨客时尚主题展餐厅"}
     * risk_fund : {"name":"ESTIMATE_ORDER_COST","amount":10000,"description":"就餐的预估费用"}
     * attach : Easdfowealsdkjfnlaksjdlfkwqoi&wl3l2sald
     * notify_url : https://api.test.com
     * openid : oUpF8uMuAJO_M2pxb1Q9zNjWeS6o
     * need_user_confirm : true
     */
    @SerializedName("out_order_no")
    private String outOrderNo;
    @SerializedName("appid")
    private String appid;
    @SerializedName("service_id")
    private String serviceId;
    @SerializedName("service_introduction")
    private String serviceIntroduction;
    @SerializedName("time_range")
    private TimeRange timeRange;
    @SerializedName("location")
    private Location location;
    @SerializedName("risk_fund")
    private RiskFund riskFund;
    @SerializedName("attach")
    private String attach;
    @SerializedName("notify_url")
    private String notifyUrl;
    @SerializedName("openid")
    private String openid;
    @SerializedName("need_user_confirm")
    private boolean needUserConfirm;
    @SerializedName("profit_sharing")
    private boolean profitSharing;
    @SerializedName("post_payments")
    private List<PostPayment> postPayments;
    @SerializedName("post_discounts")
    private List<PostDiscount> postDiscounts;
    @SerializedName("total_amount")
    private int totalAmount;
    @SerializedName("reason")
    private String reason;
    @SerializedName("goods_tag")
    private String goodsTag;
    @SerializedName("type")
    private String type;
    @SerializedName("detail")
    private Detail detail;

}
