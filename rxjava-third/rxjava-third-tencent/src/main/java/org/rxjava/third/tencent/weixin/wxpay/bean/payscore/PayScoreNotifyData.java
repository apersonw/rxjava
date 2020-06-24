package org.rxjava.third.tencent.weixin.wxpay.bean.payscore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 微信支付分确认订单跟支付回调对象
 *
 * @author doger.wang
 * @date 2020/5/14 12:18
 */
@NoArgsConstructor
@Data
public class PayScoreNotifyData implements Serializable {
    private static final long serialVersionUID = -8538014389773390989L;

    /**
     * id : EV-2018022511223320873
     * create_time : 20180225112233
     * resource_type : encrypt-resource
     * event_type : PAYSCORE.USER_CONFIRM
     * resource : {"algorithm":"AEAD_AES_256_GCM","ciphertext":"...","nonce":"...","associated_data":""}
     */
    @SerializedName("id")
    private String id;
    @SerializedName("create_time")
    private String createTime;
    @SerializedName("resource_type")
    private String resourceType;
    @SerializedName("event_type")
    private String eventType;
    @SerializedName("resource")
    private Resource resource;

    @Data
    public static class Resource implements Serializable {
        private static final long serialVersionUID = 8530711804335261449L;
        /**
         * algorithm : AEAD_AES_256_GCM
         * ciphertext : ...
         * nonce : ...
         * associated_data :
         */
        @SerializedName("algorithm")
        private String algorithm;
        @SerializedName("ciphertext")
        private String cipherText;
        @SerializedName("nonce")
        private String nonce;
        @SerializedName("associated_data")
        private String associatedData;
    }
}
