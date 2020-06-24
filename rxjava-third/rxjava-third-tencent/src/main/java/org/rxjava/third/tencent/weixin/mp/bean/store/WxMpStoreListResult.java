package org.rxjava.third.tencent.weixin.mp.bean.store;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 门店列表结果类
 */
@Data
public class WxMpStoreListResult implements Serializable {
    private static final long serialVersionUID = 5388907559949538663L;

    /**
     * 错误码，0为正常.
     */
    @SerializedName("errcode")
    private Integer errCode;
    /**
     * 错误信息.
     */
    @SerializedName("errmsg")
    private String errMsg;
    /**
     * 门店信息列表.
     */
    @SerializedName("business_list")
    private List<WxMpStoreInfo> businessList;
    /**
     * 门店信息总数.
     */
    @SerializedName("total_count")
    private Integer totalCount;

    public static WxMpStoreListResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpStoreListResult.class);
    }

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
