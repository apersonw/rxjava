package org.rxjava.third.weixin.mp.bean;

import lombok.Data;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 摇一摇周边：获取设备及用户信息接口返回JSON数据接收类
 */
@Data
public class WxMpShakeInfoResult implements Serializable {
    private static final long serialVersionUID = -1604561297395395468L;

    private Integer errcode;

    private String errmsg;

    private ShakeInfoData data;

    public static WxMpShakeInfoResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpShakeInfoResult.class);
    }

    @Data
    public class ShakeInfoData implements Serializable {
        private static final long serialVersionUID = -4828142206067489488L;

        private String page_id;

        private String openid;

        private String poi_id;

        private String brand_userame;

        private BeaconInfo beacon_info;

        @Data
        public class BeaconInfo implements Serializable {
            private static final long serialVersionUID = -8995733049982933362L;

            private double distance;

            private Integer major;

            private Integer measure_power;

            private Integer minor;

            private Integer rssi;

            private String uuid;
        }
    }

}
