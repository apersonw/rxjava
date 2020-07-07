package org.rxjava.third.weixin.pay.bean.profitsharing;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.rxjava.third.weixin.pay.bean.result.BaseWxPayResult;
import org.w3c.dom.Document;

import java.util.List;

/**
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class ProfitSharingQueryResult extends BaseWxPayResult {
    private static final long serialVersionUID = 2548673608075775067L;
    /**
     * 微信订单号
     */
    @XStreamAlias("transaction_id")
    private String transactionId;
    /**
     * 商户分账单号
     */
    @XStreamAlias("out_order_no")
    private String outOrderNo;
    /**
     * 微信分账单号
     */
    @XStreamAlias("order_id")
    private String orderId;
    /**
     * 分账单状态
     */
    @XStreamAlias("status")
    private String status;
    /**
     * 关单原因
     */
    @XStreamAlias("close_reason")
    private String closeReason;
    /**
     * 分账接收方列表
     */
    @XStreamAlias("receivers")
    private String receiversJson;
    /**
     * 分账接收方列表json转换后的对象
     */
    private List<Receiver> receivers;
    /**
     * 分账金额
     */
    @XStreamAlias("amount")
    private Integer amount;
    /**
     * 分账描述
     */
    @XStreamAlias("description")
    private String description;

    public List<Receiver> formatReceivers() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        Gson gson = gsonBuilder.create();
        final List<Receiver> receivers = gson.fromJson(receiversJson, new TypeToken<List<Receiver>>() {
        }.getType());
        this.receivers = receivers;
        return receivers;
    }

    @Override
    protected void loadXML(Document d) {
        transactionId = readXMLString(d, "transaction_id");
        outOrderNo = readXMLString(d, "out_order_no");
        orderId = readXMLString(d, "order_id");
        status = readXMLString(d, "status");
        closeReason = readXMLString(d, "close_reason");
        receiversJson = readXMLString(d, "receivers");
        amount = readXMLInteger(d, "amount");
        description = readXMLString(d, "description");
    }

    @Data
    public class Receiver {
        /**
         * 分账接收方类型
         */
        private String type;
        /**
         * 分账接收方帐号
         */
        private String account;
        /**
         * 分账金额
         */
        private Integer amount;
        /**
         * 分账描述
         */
        private String description;
        /**
         * 分账结果
         */
        private String result;
        /**
         * 分账完成时间
         */
        private String finishTime;
        /**
         * 分账失败原因
         */
        private String failReason;

        @Override
        public String toString() {
            return "Receivers{" +
                    "type='" + type + '\'' +
                    ", account='" + account + '\'' +
                    ", amount=" + amount +
                    ", description='" + description + '\'' +
                    ", result='" + result + '\'' +
                    ", finishTime='" + finishTime + '\'' +
                    ", failReason='" + failReason + '\'' +
                    '}';
        }
    }
}
