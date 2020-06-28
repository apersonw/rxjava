package org.rxjava.third.weixin.miniapp.bean.cloud;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 获取腾讯云API调用凭证结果.
 */
@Data
public class WxCloudGetQcloudTokenResult implements Serializable {
    private static final long serialVersionUID = -1505786395531138286L;

    /**
     * secretid
     */
    @SerializedName("secretid")
    private String secretId;

    /**
     * secretkey
     */
    @SerializedName("secretkey")
    private String secretKey;

    /**
     * token
     */
    @SerializedName("token")
    private String token;

    /**
     * 过期时间戳
     */
    @SerializedName("expired_time")
    private Long expiredTime;
}
