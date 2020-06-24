package org.rxjava.third.tencent.weixin.mp.bean.shake;

import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpShakeAroundRelationSearchResult implements Serializable {
    private Integer errcode;
    private String errmsg;
    private WxMpShakeAcoundRelationSearch data;

    public static WxMpShakeAroundRelationSearchResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpShakeAroundRelationSearchResult.class);
    }

    @Data
    public static class WxMpShakeAcoundRelationSearch implements Serializable {
        private List<WxMpDeviceIdentifier> relations;
        private Integer total_count;
    }
}
